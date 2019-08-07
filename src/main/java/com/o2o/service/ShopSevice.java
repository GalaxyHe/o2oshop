package com.o2o.service;

import com.o2o.dto.ShopExecution;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.pojo.Shop;
import org.apache.ibatis.annotations.Param;

import java.io.InputStream;

public interface ShopSevice {

       /**
        * @author He
        * @Description 商铺注册
        * @Date
        * @Param
        * @return
        */
    ShopExecution setShop(Shop shop, InputStream shopImgIps, String fileName);


    /**
     * @author He
     * @Description 更新商铺信息
     * @Date
     * @Param
     * @return
     */
    ShopExecution modifyShopInfo(Shop shop,InputStream shopImgIps,String fileName) throws ShopOperationException;

    /**
     * 查询指定店铺信息
     *
     * @param
     * @return Shop shop
     */
    Shop getByShopId(long shopId);


    /**
     * 分页查询店铺信息
     *
     * @param
     * @return Shop shop
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
