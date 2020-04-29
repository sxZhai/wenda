package wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import wenda.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

//    @Autowired
//    EventProducer eventProducer;


    //注册
    @RequestMapping(path = {"/reg"},method={RequestMethod.POST})
    public String reg(Model model, String username, String password,
                      @RequestParam(value = "next", required = false) String next,
                      @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/"); // 在同一应用服务器内共享cookie
                response.addCookie(cookie); // ticket下发到客户端（浏览器）存储
                // 当读取到的next字段不为空的话跳转
                if (!StringUtils.isEmpty(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @GetMapping(path = {"/reglogin"})
    public String reglogin(Model model,@RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next",next);
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(Model model, String username, String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/"); // 在同一应用服务器内共享cookie
                response.addCookie(cookie);
                if (!StringUtils.isEmpty(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            return "login";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

}
