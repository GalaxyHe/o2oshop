package com.o2o.service;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.pojo.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * @author He
     * @Description 通过个人拥有的商铺的商铺id查询旗下所有商品分类
     * @Date
     * @Param
     * @return
     **/
    List<ProductCategory> getAllCategory(long shopId);

    /**
     * 批量插入商铺分类信息
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;


    /**
     *删除指定的商品类别信息
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException;
}
