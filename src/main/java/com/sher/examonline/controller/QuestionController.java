package com.sher.examonline.controller;

import com.mysql.cj.log.Log;
import com.sher.examonline.entity.Question;
import com.sher.examonline.repository.QuestionRepository;
import com.sher.examonline.repository.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * @Title QuestionController
 * @Package com.sher.examonline.controller
 * @Description controller for question
 * @Author sher
 * @Date 2021/06/29 8:38 AM
 */
@Controller
@Slf4j
@RequestMapping("/question")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final TypeRepository typeRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, TypeRepository typeRepository) {
        this.questionRepository = questionRepository;
        this.typeRepository = typeRepository;
    }

    @PostMapping("/add")
    public String addQuestion(Question question) {
        question.setCreateTime(new Date());
        int typeId = typeRepository.getTypeID(question.getType());
        question.setTypeid(typeId);
        int res = questionRepository.addQuestion(question);
        if (res == 0) {
            System.out.println("err");
        }
        return "redirect:/";
    }

    private void queryHelper(Question question, Model model) {
        if (!"".equals(question.getType()) && question.getType() != null) {
            int typeId = typeRepository.getTypeID(question.getType());
            question.setTypeid(typeId);
        } else {
            question.setTypeid(-1);
        }
        List<Question> questions = questionRepository.findQuestion(question);
        if (questions != null) {
            for (Question q : questions) {
                String name = typeRepository.getTypeName(q.getTypeid());
                q.setType(name);
            }

            model.addAttribute("questions", questions);
        }
        List<String> nameLists = questionRepository.getAllTeacherNames();
        List<String> typeLists = typeRepository.getAllTypes();
        model.addAttribute("nameLists", nameLists);
        model.addAttribute("typeLists", typeLists);
    }

    @PostMapping("/query")
    public String queryQuestion(Question question, Model model) {
        queryHelper(question, model);
        return "queryquestion::questionlist";
    }

    @PostMapping("/querypaper")
    public String queryPaper(Question question, Model model) {
        queryHelper(question, model);
        return "manualpaper::questionlist";
    }

    @PostMapping("/del/{id}")
    @ResponseBody
    public String deleteQuestion(@PathVariable Integer id) {
        int res = questionRepository.removeQuestionById(id);
        if (res == 0) {
            log.error("Delete Question Error");
        }

        return "ok";
    }
}
