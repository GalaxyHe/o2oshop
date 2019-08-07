package com.o2o.dao;

import com.o2o.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductDao {
    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     *
     * @param productId
     * @return
     */
    Product queryProductByProductId(long productId);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);


    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id,商品类别
     *
     * @param productCondition
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(
            @Param("productCondition") Product productCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);


    /**
     * 查询对应的商品总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);


    /**
     * 删除商品类别之前，将商品类别ID置为空
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);


    /**
      * @author He
      * @Description 用户购买商品的操作
      * @Date
      * @Param productId--要购买的商品的id
      * @return
      */
    int decreaseProductInventory(long productId);

    /**
     * @author He
     * @Description 进行一次购买操作，我们就从数据库中查找商品库存是否大于0
     * 在sql语句中加上了 for update ，使用了悲观锁，避免了高并发业务下，库存出现负数的情况。
     * @Date
     * @Param productId--要购买的商品的id
     * @return
     */
    Product searchProductInventory(long productId);

}
