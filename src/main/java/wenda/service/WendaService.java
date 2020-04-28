package wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenda.dao.UserDAO;

import javax.annotation.Resource;


@Service
public class WendaService {

    public String getMessage(int userId) {

        return "Hello Message:" + String.valueOf(userId);
    }
}

