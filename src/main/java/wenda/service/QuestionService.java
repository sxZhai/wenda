package wenda.service;

import org.springframework.web.util.HtmlUtils;
import wenda.dao.QuestionDAO;
import wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/15.
 */
@Service
public class QuestionService {
    @Resource
    QuestionDAO questionDAO;
    @Resource
    SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int addQuestion(Question question) {
        // HTML代码过滤，就是把html语言进行转义
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0; // 这里question.getId()就是存进数据库后对应的Id了
    }

    public Question selectQuestionById(int id) {
        return questionDAO.selectQuestionById(id);
    }

    //更新评论的数量
//    public int updateCommentCount(int id, int comment_count) {
//        return questionDAO.updateCommentCount(id, comment_count);
//    }


}
