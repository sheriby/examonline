package com.sher.examonline.vo;

import lombok.Data;

/**
 * @Title AutoPaper
 * @Package com.sher.examonline.vi
 * @Description auto paper vo
 * @Author sher
 * @Date 2021/06/30 10:38 AM
 */
@Data
public class AutoPaper {

    private String teacherName;
    private String title;
    private Integer examTime;
    private String difficulty;
    private Integer number;
}
