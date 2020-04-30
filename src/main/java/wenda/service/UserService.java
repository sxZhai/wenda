package wenda.service;

import org.springframework.util.StringUtils;
import sun.security.provider.MD5;
import wenda.dao.LoginTicketDAO;
import wenda.dao.UserDAO;
import wenda.model.LoginTicket;
import wenda.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenda.utils.WendaUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserDAO userDAO;

    @Resource
    private LoginTicketDAO loginTicketDAO;

    public Map<String,String> register(String username,String password){
        Map<String,String> map=new HashMap<String, String>();
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user= userDAO.selectByName(username);
        if(user!=null){
            map.put("msg","用户名已被注册");
            return map;
        }

        user=new User();
        Random random=new Random();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,Object> login(String username, String password){
        Map<String,Object> map = new HashMap<>();
        // 后台简单判断
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user == null){
            map.put("msg","用户名不存在");
            return map;
        }
        if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        // 登陆成功下发ticket之后自动登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String addLoginTicket(int user_id){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(user_id);
        Date nowDate = new Date();
        nowDate.setTime(nowDate.getTime() + 1000*3600*24);
        ticket.setExpired(nowDate);
        ticket.setStatus(0);
        // UUID生成的随机ticket有"-"，要替换掉
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket(); // 返回ticket
    }
    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public  User selectUserByName(String name){
        return userDAO.selectByName(name);
    }

}
