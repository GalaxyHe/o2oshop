package com.o2o.dao;

import com.o2o.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductCategoryDao {

    /**
     * @author He
     * @Description 通过个人拥有的商铺的商铺id查询旗下所有商品分类
     * @Date
     * @Param
     * @return
     **/
     List<ProductCategory> queryProducetCategoryList(long shopId);


    /**
     * 批量新增商品类别
     *
     * @param
     *
     * @return effectedNum
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);


    /**
     * 删除商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(
            @Param("productCategoryId") long productCategoryId,
            @Param("shopId") long shopId);
}
