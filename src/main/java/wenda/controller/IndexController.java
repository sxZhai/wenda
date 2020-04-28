package wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wenda.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


//@Controller
public class IndexController {
    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public String index(HttpSession session){
        return "index"+session.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{ueserId}"})
    @ResponseBody
    public String profile(@PathVariable("ueserId") int uesid,
                          @RequestParam("type") int type,
                          @RequestParam("key") int key){
        return String.format("profile page of %d,type is %d,key is %d",uesid,type,key);
    }


    @RequestMapping(path={"/vm"},method={RequestMethod.GET})
    public String template(Model model){
        List<String> colors= Arrays.asList(new String[] {"RED","GREEN","YELLOW"});
        model.addAttribute("color",colors);
        model.addAttribute("user", new User("LEE"));
        return "home";
    }

    @RequestMapping(path={"/request"},method={RequestMethod.GET})
    @ResponseBody
    public String template(Model model,
                           HttpServletRequest request,
                           HttpServletResponse response){
        StringBuilder sb=new StringBuilder();
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        return sb.toString();
    }

    @RequestMapping(path={"/redirect/{code}"},method={RequestMethod.GET})
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){
        session.setAttribute("msg","jump from 301");
        return "redirect:/";
    }

}
