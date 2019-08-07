package com.o2o.pojo;


/**
 * @author He
 * @Date 2019/7/29
 * @Time 15:54
 * @Description TODO
 **/

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonInfo {

    private Long userId;
    private String name;
    private String password;
    private String profileImg;
    private String email;
    private String gender;
    private Integer enableStatus;
    //1代表顾客 2代表商家 3代表超级管理员
    private Integer userType;
    private Date createTime;
    private Date lastEditTime;


}
