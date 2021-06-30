package com.sher.examonline.repository;

import com.sher.examonline.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title QuestionRepository
 * @Package com.sher.examonline.repository
 * @Description repository for question
 * @Author sher
 * @Date 2021/06/29 9:42 AM
 */
@Repository
@Slf4j
public class QuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addQuestion(Question question) {
        int res;
        try {
            String sql = "insert into exam_question(content, difficulty, type, teacherName, createTime, isselect) values (?, ?, ?, ?, ?, ?)";
            res = jdbcTemplate.update(sql, question.getContent(),
                    question.getDifficulty(), question.getTypeid(),
                    question.getTeacherName(), question.getCreateTime(), question.getIsselect());

        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public List<String> getAllTeacherNames() {
        return jdbcTemplate.queryForList("select distinct teacherName from exam_question", String.class);
    }

    private RowMapper<Question> usermapper = (resultSet, i)->{
        Question question = new Question();
        question.setId(resultSet.getInt("id"));
        question.setTeacherName(resultSet.getString("teacherName"));
        question.setTypeid(resultSet.getInt("type"));
        question.setDifficulty(resultSet.getString("difficulty"));
        question.setContent(resultSet.getString("content"));
        question.setIsselect(resultSet.getInt("isselect"));

        return question;
    };

    public Question getQuestionById(int id) {
        return jdbcTemplate.queryForObject("select * from exam_question where id = ?", new Object[]{id}, usermapper);
    }

    private boolean empty(String str) {
        return str == null || "".equals(str);
    }

    public List<Question> findQuestion(Question question) {
        String teacherName = question.getTeacherName();
        String difficulty = question.getDifficulty();
        int type = question.getTypeid();
        String content = question.getContent();

        if (empty(teacherName) && empty(difficulty) && empty(content) && type == -1) {
            return jdbcTemplate.query("select * from exam_question", usermapper);
        }
        List<Question> questions = null;
        if (!empty(content)) {
             questions = jdbcTemplate.query("select * from exam_question where content like ?", new Object[]{"%" + content + "%"}, usermapper);
        }

        if (!empty(teacherName)) {
            if (questions == null) {
                questions = jdbcTemplate.query("select * from exam_question where teacherName = ?", new Object[]{teacherName}, usermapper);
            } else {
                questions = questions.stream().filter(q -> q.getTeacherName().equals(teacherName)
                ).collect(Collectors.toList());
            }
        }

        if (type != -1) {
            if (questions == null) {
                questions = jdbcTemplate.query("select * from exam_question where type = ?", new Object[]{type}, usermapper);
            } else {
                questions = questions.stream().filter(q -> q.getTypeid() == type
                ).collect(Collectors.toList());
            }
        }
        if (!empty(difficulty)) {
            if (questions == null) {
                questions = jdbcTemplate.query("select * from exam_question where difficulty = ?", new Object[]{difficulty}, usermapper);
            } else {
                questions = questions.stream().filter(q -> q.getDifficulty().equals(difficulty)
                ).collect(Collectors.toList());
            }
        }

        return questions;
    }

    public List<Question> getSomeQuestionByDifficulty(int n, String difficulty) {
        if (n == 0) {
            return new ArrayList<>();
        }
        List<Question> questions = jdbcTemplate.query("select * from exam_question where difficulty = ?",
                new Object[]{difficulty}, usermapper);
        Collections.shuffle(questions);
        return questions.subList(0, n);
    }

    public int removeQuestionById(int id) {
        int res;
        try {
            res = jdbcTemplate.update("delete from exam_question where id = ?", id);
        } catch (Exception e) {
            return 0;
        }
        return res;
    }
}
