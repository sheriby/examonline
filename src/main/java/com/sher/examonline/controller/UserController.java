package com.sher.examonline.controller;

import com.sher.examonline.entity.User;
import com.sher.examonline.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Title UserController
 * @Package com.sher.examonline.controller
 * @Description controller for user
 * @Author sher
 * @Date 2021/06/28 10:51 AM
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signup(User user) {
        user.setRole("user");
        int res = userRepository.addUser(user);
        if (res == 0) {
            System.out.println("error");
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        User u = userRepository.verifyUser(user);
        if (u != null) {
            u.setPassword(null);
            session.setAttribute("user", u);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
}
