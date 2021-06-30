package com.sher.examonline.repository;

import com.sher.examonline.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Title AnswerRepository
 * @Package com.sher.examonline.repository
 * @Description repository for answer
 * @Author sher
 * @Date 2021/06/30 2:39 PM
 */
@Repository
public class AnswerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveAnswer(Answer answer) {
        int res = 0;
        try {
            res = jdbcTemplate.update("insert into exam_answer(paper, name, answer) values (?, ?, ?)",
                    answer.getPaper(), answer.getName(), answer.getAnswer());
        } catch (Exception e) {
            return 0;
        }
        return res;
    }
}
