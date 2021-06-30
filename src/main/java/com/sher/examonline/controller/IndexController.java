package com.sher.examonline.controller;

import com.sher.examonline.entity.Question;
import com.sher.examonline.repository.QuestionRepository;
import com.sher.examonline.repository.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Title IndexController
 * @Package com.sher.examonline.controller
 * @Description controller for index
 * @Author sher
 * @Date 2021/06/28 8:59 AM
 */
@Controller
@Slf4j
public class IndexController {

    private final QuestionRepository questionRepository;
    private final TypeRepository typeRepository;

    @Autowired
    public IndexController(QuestionRepository questionRepository, TypeRepository typeRepository) {
        this.questionRepository = questionRepository;
        this.typeRepository = typeRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/addq")
    public String addq() {
        return "addquestion";
    }

    @GetMapping("/queryq")
    public String queryq(Model model) {
        List<String> nameLists = questionRepository.getAllTeacherNames();
        List<String> typeLists = typeRepository.getAllTypes();
        model.addAttribute("nameLists", nameLists);
        model.addAttribute("typeLists", typeLists);
        return "queryquestion";
    }

    @GetMapping("/paper")
    public String paper() {
        return "paper";
    }

    @GetMapping("/manualpaper")
    public String manualpaper(Model model) {
        List<String> nameLists = questionRepository.getAllTeacherNames();
        List<String> typeLists = typeRepository.getAllTypes();
        model.addAttribute("nameLists", nameLists);
        model.addAttribute("typeLists", typeLists);
        return "manualpaper";
    }

    @GetMapping("/autopaper")
    public String autopaper() {
        return "autopaper";
    }

    @GetMapping("/onlineexam")
    public String onlineexam() {
        return "chooseexam";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
