package com.o2o.pojo;

import lombok.*;

import java.util.Date;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;



}
