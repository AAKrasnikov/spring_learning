package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginFrom;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    Logger logger = Logger.getLogger(LoginService.class);
    public boolean authenticate(LoginFrom loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        return loginFrom.getUsername().equals("root") && loginFrom.getPassword().equals("123");
    }
}
