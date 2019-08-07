package com.o2o.dao;

import com.o2o.pojo.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ShopCategoryDao {

     /**
      * @author He
      * @Description 展示商铺分类列表
      * @Date
      * @Param
      * @return
      */
     List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);



}
