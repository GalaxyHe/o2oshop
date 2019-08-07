package com.o2o.dao;

import com.o2o.pojo.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopDao {

    /**
      * @author He
      * @Description 商铺注册
      * @Date 19:20 2019/7/29
      * @Param Shop shop
      * @return
      */

    int insertShop(Shop shop);



     /**
      * @author He
      * @Description 修改商铺信息
      * @Date  0:07 2019/7/29
      * @Param shop
      * @return
      */
    int updateShop(Shop shop);

    /**
     * 通过shop id 查询店铺
     * @param shopId
     * @return shop
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
     *
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     *
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
