package com.sher.examonline.entity;

import lombok.Data;

/**
 * @Title Paper
 * @Package com.sher.examonline.entity
 * @Description paper entity
 * @Author sher
 * @Date 2021/06/30 9:11 AM
 */
@Data
public class Paper {

    private Integer id;
    private String teacherName;
    private String title;
    private String examTime;
    private String questions;
}
