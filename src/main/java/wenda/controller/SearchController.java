package wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenda.model.EntityType;
import wenda.model.Question;
import wenda.model.ViewObject;
import wenda.service.FollowService;
import wenda.service.QuestionService;
import wenda.service.SearchService;
import wenda.service.UserService;
import wenda.utils.WendaUtil;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    // 保存数据库所有question到es
//    @GetMapping(value = "/save")
//    @ResponseBody
//    public String save() {
//        List<Question> questions = questionService.selectLatestQuestions(0, 0, 500); // 500是按数据量写的
//        searchService.save(questions);
//        return WendaUtil.getJsonString(0);
//    }

    @GetMapping(value = "/search")
    public String search(Model model,
                         @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.SearchQuestion(keyword, offset, count,"<em>","</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q=questionService.selectQuestionById(question.getId());
                ViewObject vo = new ViewObject();
                if(question.getContent()!=null){
                    q.setContent(question.getContent());
                }
                if(question.getTitle()!=null){
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, q.getId()));
                vo.set("user", userService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索失败" + e.getMessage());
        }
        return "result";
    }
}
