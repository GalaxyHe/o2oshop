package com.o2o.service.impl;

import com.o2o.dao.ShopDao;
import com.o2o.dto.ShopExecution;
import com.o2o.enums.ShopStateEnum;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.pojo.Shop;
import com.o2o.service.ShopSevice;
import com.o2o.util.ImageUtil;
import com.o2o.util.PageCalculator;
import com.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 10:39
 * @Description TODO
 **/


@Service
public class ShopServiceImpl implements ShopSevice {
    private final ShopDao shopDao;

    @Autowired
    public ShopServiceImpl(ShopDao shopDao) {
        this.shopDao = shopDao;
    }


    @Override
    @Transactional
    public ShopExecution setShop(Shop shop, InputStream shopImgIps, String fileName) {
        //传入过来的shop对象如果为空，调用shopstateEnum枚举类的空状态
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        }

        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            //调用dao层方法，向数据库插入注册商铺信息
            int res = shopDao.insertShop(shop);
            if (res <= 0) {
                throw new ShopOperationException("Failed to add shop to database!");
            } else {
                if (shopImgIps != null) {
                    try {
                        addShopImg(shop, shopImgIps, fileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("Failed to add image address!" + e.getMessage());
                    }

                    //更新数据库中店铺的图片地址
                    int res2 = shopDao.updateShop(shop);
                    //如果传回来的结果小于0，则更新图片地址失败
                    if (res2 <= 0) {
                        throw new ShopOperationException("Failed to update image address!");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("Failed to add shop!" + e.getMessage());
        }

        //操作成功，返回成功状态标识
        return new ShopExecution(ShopStateEnum.SUCCESS, shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution modifyShopInfo(Shop shop, InputStream shopImgIps, String fileName)throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        } else {
            //判断是否需要处理图片
            try {
                if (shopImgIps != null && fileName != null && !"".equals(fileName)) {
                    Shop tmpShop = shopDao.queryByShopId(shop.getShopId());
                    if (tmpShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tmpShop.getShopImg());
                    }
                    addShopImg(shop, shopImgIps, fileName);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int res = shopDao.updateShop(shop);
                if (res <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("Modify shop information failed!" + e.getMessage());
            }
        }
    }

    /**
        * @author He
        * @Description 获取注册时图片地址，并将该地址信息设置给shop
        * @Date
        * @Param
        * @return
        */
    private void addShopImg(Shop shop, InputStream shopImgIps,String fileName) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddress = ImageUtil.generateThumbnail(shopImgIps,fileName,dest);
        shop.setShopImg(shopImgAddress);
    }

    /**
     * 分页查询店铺信息
     *
     * @param  shopCondition, pageIndex,pageSize
     * @return  shopExecution
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if(shopList != null){
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else{
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return shopExecution;
    }
}
