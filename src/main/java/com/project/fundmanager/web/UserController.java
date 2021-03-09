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
    public Map<String, Object> login(@RequestBody Map<String,String> postData, HttpSession session) {
        try {
            String email = postData.get("email");
            String password = postData.get("password");
            User user = userService.login(email, password);
            session.setAttribute("id",user.getId());
            return Map.of("message","success","user", new slimUser(user));
        } catch (Exception e) {
            return Map.of("error", "SIGNIN_FAILED", "message", e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    public Map<String, Object> register(@RequestBody Map<String,String> postData) {
        String email = postData.get("email");
        String password = postData.get("password");
        String name = postData.get("name");
        try {
            userService.register(email,password,name);
            return Map.of("message","success");
        } catch (Exception e) {
            return Map.of("error", "Register_FAILED", "message", e.getMessage());
        }
    }

    @PostMapping(value = "/update/{attr}")
    public Map<String, Object> updateUser(@RequestBody Map<String,String> postData,@PathVariable String attr,@SessionAttribute long id) {
        String name = postData.get("name");
        String password = postData.get("password");
        try {
            switch (attr) {
                case "name" -> {
                    if (name==null){
                        throw new RuntimeException("Name can't be empty");
                    }
                    userService.updateUserName(id, name);
                }
                case "password" -> {
                    if (password==null){
                        throw new RuntimeException("Name can't be empty");
                    }
                    userService.updateUserPassword(id, password);
                }
            }
            return Map.of("message","success");
        } catch (Exception e) {
            return Map.of("error", "UPDATE_FAILED", "message", e.getMessage());
        }
    }

    @GetMapping(value = "/getList")
    public Map<String, Object> getUserList(@RequestParam(defaultValue= "0") int offset, @RequestParam(defaultValue= "100") int maxResults) {
        try {
            return Map.of("success",userService.getUserList(offset,maxResults));
        } catch (Exception e) {
            return Map.of("error", "AUTH_FAILED", "message", e.getMessage());
        }
    }
}
