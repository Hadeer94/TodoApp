package org.example.util;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.example.security.AppUser;
import org.example.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeInitializer implements CommandLineRunner {
    private final Log logger = LogFactory.getLog(FirstTimeInitializer.class);
    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {

        if(userService.findAll().isEmpty()){
            logger.info("no users accounts creating some users");
            AppUser user = new AppUser("user@todo.com","123");
            userService.save(user);
        }
    }
}
