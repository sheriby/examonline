package com.sher.examonline.entity;

import lombok.Data;

import java.util.Date;


/**
 * @Title Question
 * @Package com.sher.examonline.entity
 * @Description question entity
 * @Author sher
 * @Date 2021/06/29 8:39 AM
 */
@Data
public class Question {

    private Integer id;
    private String content;
    private String difficulty;
    private Integer typeid;
    private String type;
    private String teacherName;
    private Date createTime;
    private Integer isselect;
}
