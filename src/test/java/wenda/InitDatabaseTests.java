package wenda;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import wenda.dao.QuestionDAO;
import wenda.dao.UserDAO;
import wenda.model.Question;
import wenda.model.User;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@SpringBootTest


class InitDatabaseTests {
//    @Resource
//    UserDAO userDAO;
//    @Resource
//    QuestionDAO questionDAO;
//
//    @Test
//    public void contextLoads() {
//        Random random = new Random();
//        for (int i = 0; i < 11; ++i) {
//            User user = new User();
//            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//            user.setName(String.format("USER%d", i));
//            user.setPassword("");
//            user.setSalt("");
//            System.out.print(user.getHeadUrl());
//            userDAO.addUser(user);
//
//            user.setPassword("newpassword");
//            userDAO.updatePassword(user);
//
//            Question question = new Question();
//            question.setCommentCount(i);
//            Date date = new Date();
//            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
//            question.setCreatedDate(date);
//            question.setUserId(i + 1);
//            question.setTitle(String.format("TITLE{%d}", i));
//            question.setContent(String.format("Balaababalalalal Content %d", i));
//            questionDAO.addQuestion(question);
//        }
//
//        //System.out.print(questionDAO.selectLatestQuestions(0,0,10));
//
//    }
}
