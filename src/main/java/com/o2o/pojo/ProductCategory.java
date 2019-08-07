package com.o2o.pojo;

import lombok.*;

import java.util.Date;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCategory {
    private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private Integer priority;
    private Date createTime;

}
