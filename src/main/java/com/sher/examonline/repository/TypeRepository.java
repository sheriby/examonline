package com.sher.examonline.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title TypeRepository
 * @Package com.sher.examonline.repository
 * @Description repository for type
 * @Author sher
 * @Date 2021/06/29 9:52 AM
 */
@Repository
public class TypeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTypeID(String type) {
        Integer res;
        try {
            res = jdbcTemplate.queryForObject("select id from exam_question_type where name = ?", new Object[]{type}, Integer.class);
        } catch (Exception e) {
            res = null;
        }
        if (res == null) {
            jdbcTemplate.update("insert into exam_question_type(name) values (?)", type);
            res = jdbcTemplate.queryForObject("select id from exam_question_type where name = ?", new Object[]{type}, Integer.class);
            return res;
        }
        return res;
    }

    public String getTypeName(int type) {
        String res = null;
        try {
            res = jdbcTemplate.queryForObject("select name from exam_question_type where id = ?", new Object[]{type}, String.class);
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public List<String> getAllTypes() {
        return jdbcTemplate.queryForList("select name from exam_question_type", String.class);
    }
}
