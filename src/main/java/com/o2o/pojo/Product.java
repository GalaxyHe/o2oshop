package com.o2o.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = -349433539553804024L;

    private Long productId;
    private String productName;
    private String productDesc;
    private String imgAddr;// 简略图
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;
    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;
    //商品库存
    private Integer inventory;




}
