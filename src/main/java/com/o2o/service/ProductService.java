package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.pojo.Product;

import java.util.List;

public interface ProductService {

     /**
      * @author He
      * @Description 添加商品
      * @Date
      * @Param
      * @return
      */
     ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imgHolderlist)
             throws ProductOperationException;


    /**
     * @author He
     * @Description 根据商品的id查询商品
     * @Date
     * @Param
     * @return
     */
    Product getProductById(long productId);


    /**
     * @author He
     * @Description 修改商品信息
     * @Date
     * @Param
     * @return
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImglist)
            throws ProductOperationException;


    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    int getProduct(long productId);


}
