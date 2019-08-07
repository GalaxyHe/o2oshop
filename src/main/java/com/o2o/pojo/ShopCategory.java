package com.o2o.pojo;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopCategory {
	private Long shopCategoryId;
	private String shopCategoryName;
	private String shopCategoryDesc;
	private String shopCategoryImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private ShopCategory parent;
	private Integer parentId;

}
