package wenda.controller;

import org.springframework.web.bind.annotation.*;
import wenda.model.*;
//import wenda.model.ViewObject;
import wenda.service.CommentService;
import wenda.service.FollowService;
import wenda.service.QuestionService;
import wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;

    // Jenkins test
//    @GetMapping(path = "/user/{userId}")
//    public String userIndex(Model model, @PathVariable("userId") int userId) {
//        model.addAttribute("vos", getQuestions(userId, 0, 10));
//        // 显示关注和被关注列表
//        User user = userService.getUser(userId);
//        ViewObject vo = new ViewObject();
//        vo.set("user", user);
//        vo.set("commentCount", commentService.getUserCommentCount(userId));
//        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
//        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
//        if (hostHolder.getUser() != null) {
//            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), userId, EntityType.ENTITY_USER));
//        } else {
//            vo.set("followed", false);
//        }
//        model.addAttribute("profileUser", vo);
//        model.addAttribute("vos", getQuestions(userId, 0, 10));
//        return "index";
//    }

    @GetMapping(value = "/user/{userId}")
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        // 显示关注和被关注列表
        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "profile";
    }

    @GetMapping(path = {"/", "/index"})
    public String home(Model model) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";

    }

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            //问题关注的数量
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}