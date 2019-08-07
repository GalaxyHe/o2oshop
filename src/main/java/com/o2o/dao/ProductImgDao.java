package com.o2o.dao;

import com.o2o.pojo.ProductImg;

import java.util.List;

public interface ProductImgDao {

     /**
      * @author He
      * @Description 批量插入商品图片
      * @Date
      * @Param
      * @return
      */
    int batchInsertProductImg(List<ProductImg> productImgList);


    /**
     * @author He
     * @Description 根据商品的id删除对应的图片
     * @Date
     * @Param
     * @return
     */
    int deleteProductImgByProductId(long productId);


    List<ProductImg> queryProductImgList(long productId);
}
