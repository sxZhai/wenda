package wenda;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import wenda.dao.QuestionDAO;
import wenda.dao.UserDAO;
import wenda.model.Question;
import wenda.model.User;
import wenda.service.QuestionService;
import wenda.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@SpringBootTest


class ReadDataTests {
    @Resource
    UserDAO userDAO;
    @Resource
    QuestionDAO questionDAO;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 9; ++i) {
            System.out.print(userService.getUser(i+1).getHeadUrl());
        }

        //System.out.print(questionDAO.selectLatestQuestions(0,0,10));

    }
}
