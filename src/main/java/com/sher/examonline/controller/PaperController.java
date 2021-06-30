package com.sher.examonline.controller;

import com.sher.examonline.entity.Answer;
import com.sher.examonline.entity.Paper;
import com.sher.examonline.entity.Question;
import com.sher.examonline.repository.AnswerRepository;
import com.sher.examonline.repository.PaperRepository;
import com.sher.examonline.vo.AutoPaper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Title PaperController
 * @Package com.sher.examonline.controller
 * @Description controller for paper
 * @Author sher
 * @Date 2021/06/30 9:11 AM
 */
@Controller
@Slf4j
@RequestMapping("/paper")
public class PaperController {

    private final PaperRepository paperRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public PaperController(PaperRepository paperRepository, AnswerRepository answerRepository) {
        this.paperRepository = paperRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/preview")
    public String preview(Paper paper, Model model) {
        int res = paperRepository.savePaper(paper);
        paper.setId(res);
        List<Question> questions = paperRepository.getPaperQuestion(paper);

        model.addAttribute("paper", paper);
        model.addAttribute("questions", questions);

        return "paperpreview";
    }

    @PostMapping("/autopaper")
    public String autopaper(AutoPaper autoPaper, Model model) {
        PaperRepository.PaperQuestion paperQuestion = paperRepository.autoPaper(autoPaper);
        model.addAttribute("paper", paperQuestion.paper);
        model.addAttribute("questions", paperQuestion.questions);
        return "paperpreview";
    }

    @PostMapping("/exam")
    public String exam(Integer paperId, Model model) {
        Paper paper = paperRepository.getPaperById(paperId);
        List<Question> questions = paperRepository.getPaperQuestion(paper);
        model.addAttribute("paper", paper);
        model.addAttribute("questions", questions);

        return "exam";
    }

    @PostMapping("/answer")
    public String answer(Answer answer) {
        int res = answerRepository.saveAnswer(answer);
        if (res == 0) {
            log.error("Error when saving answer");
        }

        return "redirect:/";
    }
}
