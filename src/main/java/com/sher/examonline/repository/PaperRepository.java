package com.sher.examonline.repository;

import com.sher.examonline.entity.Paper;
import com.sher.examonline.entity.Question;
import com.sher.examonline.vo.AutoPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Title PaperRepository
 * @Package com.sher.examonline.repository
 * @Description repository for paper
 * @Author sher
 * @Date 2021/06/30 9:25 AM
 */
@Repository
public class PaperRepository {

    private final JdbcTemplate jdbcTemplate;
    private final QuestionRepository questionRepository;

    @Autowired
    public PaperRepository(JdbcTemplate jdbcTemplate, QuestionRepository questionRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionRepository = questionRepository;
    }

    public int savePaper(Paper paper) {
        int id = new Random().nextInt(99999999) + 100000000;
        paper.setId(id);
        jdbcTemplate.update("insert into exam_paper(id, teachername, examtime, createTime, title) values (?, ?, ?, ?, ?)",
                id, paper.getTeacherName(), paper.getExamTime(), new Date(), paper.getTitle());
        String[] questionids = paper.getQuestions().split(";");
        for (String qid : questionids) {
            jdbcTemplate.update("insert into exam_paper_questions(exam_paper, exam_question) values (?, ?)", id, Integer.parseInt(qid));
        }

        return id;
    }

    public List<Question> getPaperQuestion(Paper paper) {
        List<Integer> qids = jdbcTemplate.queryForList("select exam_question from exam_paper_questions where exam_paper = ?",
                new Object[]{paper.getId()}, Integer.class);
        List<Question> questions = new ArrayList<>();
        for (Integer qid : qids) {
            questions.add(questionRepository.getQuestionById(qid));
        }

        return questions;
    }

    public class PaperQuestion {
        public Paper paper;
        public List<Question> questions;

        public PaperQuestion() {
            paper = new Paper();
            questions = new ArrayList<>();
        }
    }

    public PaperQuestion autoPaper(AutoPaper autoPaper) {
        PaperQuestion paperQuestion = new PaperQuestion();
        Paper paper = paperQuestion.paper;

        paper.setExamTime(String.valueOf(autoPaper.getExamTime()));
        paper.setTeacherName(autoPaper.getTeacherName());
        paper.setTitle(autoPaper.getTitle());

        int number = autoPaper.getNumber();
        List<Question> questions = paperQuestion.questions;
        if ("简单".equals(autoPaper.getDifficulty())) {
            int easy = (int) (number * 0.8);
            int simple = number - easy;
            List<Question> easyq = questionRepository.getSomeQuestionByDifficulty(easy, "简单");
            List<Question> easys = questionRepository.getSomeQuestionByDifficulty(simple, "中等");
            questions.addAll(easyq);
            questions.addAll(easys);
        }
        if ("中等".equals(autoPaper.getDifficulty())) {
            int easy = (int) (number * 0.7);
            int simple = (int) (number * 0.2);
            int middle = number - easy -simple;
            List<Question> easyq = questionRepository.getSomeQuestionByDifficulty(easy, "简单");
            List<Question> easys = questionRepository.getSomeQuestionByDifficulty(simple, "中等");
            List<Question> easym = questionRepository.getSomeQuestionByDifficulty(middle, "较难");
            questions.addAll(easyq);
            questions.addAll(easys);
            questions.addAll(easym);
        }
        if ("较难".equals(autoPaper.getDifficulty())) {
            int easy = (int) (number * 0.6);
            int simple = (int) (number * 0.2);
            int middle = (int) (number * 0.1);
            int hard = number - easy - simple - middle;
            List<Question> easyq = questionRepository.getSomeQuestionByDifficulty(easy, "简单");
            List<Question> easys = questionRepository.getSomeQuestionByDifficulty(simple, "中等");
            List<Question> easym = questionRepository.getSomeQuestionByDifficulty(middle, "较难");
            List<Question> easyh = questionRepository.getSomeQuestionByDifficulty(hard, "困难");
            questions.addAll(easyq);
            questions.addAll(easys);
            questions.addAll(easym);
            questions.addAll(easyh);
        }
        if ("困难".equals(autoPaper.getDifficulty())) {
            int easy = (int) (number * 0.5);
            int simple = (int) (number * 0.2);
            int middle = (int) (number * 0.2);
            int hard = number - easy - simple - middle;
            List<Question> easyq = questionRepository.getSomeQuestionByDifficulty(easy, "简单");
            List<Question> easys = questionRepository.getSomeQuestionByDifficulty(simple, "简单");
            List<Question> easym = questionRepository.getSomeQuestionByDifficulty(middle, "较难");
            List<Question> easyh = questionRepository.getSomeQuestionByDifficulty(hard, "困难");
            questions.addAll(easyq);
            questions.addAll(easys);
            questions.addAll(easym);
            questions.addAll(easyh);
        }

        StringBuilder sb = new StringBuilder();
        for (Question q : questions) {
            sb.append(q.getId());
            sb.append(";");
        }
        paper.setQuestions(sb.toString());
        savePaper(paper);
        return paperQuestion;
    }

    private RowMapper<Paper> paperRowMapper = (resultSet, i)->{
        Paper paper = new Paper();
        paper.setId(resultSet.getInt("id"));
        paper.setTitle(resultSet.getString("title"));
        paper.setTeacherName(resultSet.getString("teachername"));
        paper.setExamTime(resultSet.getString("examtime"));

        return paper;
    };

    public Paper getPaperById(Integer id) {
        Paper paper = null;
        try {
            paper = jdbcTemplate.queryForObject("select * from exam_paper where id = ?", new Object[]{id}, paperRowMapper);
        } catch (Exception e) {
            return null;
        }
        return paper;
    }

}
