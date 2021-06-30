package com.sher.examonline.repository;

import com.sher.examonline.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.naming.ldap.PagedResultsControl;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Title UserRepository
 * @Package com.sher.examonline.repository
 * @Description user repo
 * @Author sher
 * @Date 2021/06/28 10:59 AM
 */
@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addUser(User user) {
        int res;
        try {
            res = jdbcTemplate.update("insert into user(username, password, role) values (?, ?, ?)",
                    user.getUsername(), user.getPassword(), user.getRole());
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return res;
    }

    private RowMapper<User> userRowMapper = (resultSet, i) -> {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setRole(resultSet.getString("role"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));

        return user;
    };

    public User verifyUser(User user) {
        Object[] args = new Object[2];
        args[0] = user.getUsername();
        args[1] = user.getPassword();
        User res = null;
        try {
            res = jdbcTemplate.queryForObject("select * from user where username = ? and password = ?", args, userRowMapper);
        } catch (Exception e) {
            return null;
        }
        return res;
    }
}
