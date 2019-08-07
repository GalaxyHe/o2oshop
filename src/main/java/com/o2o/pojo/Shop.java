package com.o2o.pojo;

import lombok.*;
import java.util.Date;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shop {
	private Long shopId;
	private String shopName;
	private String shopDesc;
	private String shopAddr;
	private String phone;
	private String shopImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;
	private String advice;
	private Area area;
	private PersonInfo owner;
	private ShopCategory shopCategory;

}
