package com.o2o.service;

import com.o2o.pojo.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory> getAll(ShopCategory shopCategoryCondition);

}
