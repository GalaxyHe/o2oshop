package com.o2o.pojo;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Area {
    //区域id
    private Integer areaId;
    //区域名字
    private String areaName;
    //权重
    private Integer priority;
    //加入时间
    private Date createTime;
    //最近一次编辑时间
    private Date lastEditTime;


}
