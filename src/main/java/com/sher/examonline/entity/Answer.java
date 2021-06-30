package com.sher.examonline.entity;

import lombok.Data;

/**
 * @Title Answer
 * @Package com.sher.examonline.entity
 * @Description answer entity
 * @Author sher
 * @Date 2021/06/30 2:35 PM
 */
@Data
public class Answer {

    private Integer id;
    private String name;
    private Integer paper;
    private String answer;
}
