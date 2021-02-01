package com.project.fundmanager.web;

import com.project.fundmanager.entity.User;
import com.project.fundmanager.entity.slimUser;
import com.project.fundmanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        try {
            User user = userService.login(email, password);
            session.setAttribute("id",user.getId());
            return Map.of("message","success","user", new slimUser(user));
        } catch (Exception e) {
            return Map.of("error", "SIGNIN_FAILED", "message", e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    public Map<String, Object> register(@RequestParam String email,@RequestParam String password,@RequestParam String name) {
        try {
            userService.register(email,password,name);
            return Map.of("message","success");
        } catch (Exception e) {
            return Map.of("error", "SIGNIN_FAILED", "message", e.getMessage());
        }
    }

    @PostMapping(value = "/update/{attr}")
    public Map<String, Object> updateUser(@PathVariable String attr,@SessionAttribute long id,
                                          @RequestParam String name,@RequestParam String password) {
        try {
            switch (attr) {
                case "name" -> userService.updateUserName(id, name);
                case "password" -> userService.updateUserPassword(id, password);
            }
            return Map.of("message","success");
        } catch (Exception e) {
            return Map.of("error", "SIGNIN_FAILED", "message", e.getMessage());
        }
    }

    @GetMapping(value = "/getList")
    public Map<String, Object> getUserList(@RequestParam(defaultValue= "0") int offset, @RequestParam(defaultValue= "100") int maxResults) {
        try {
            return Map.of("success",userService.getUserList(offset,maxResults));
        } catch (Exception e) {
            return Map.of("error", "SIGNIN_FAILED", "message", e.getMessage());
        }
    }
}
