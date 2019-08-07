package com.o2o.dto;

import com.o2o.enums.ShopStateEnum;
import com.o2o.pojo.Shop;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 10:25
 * @Description TODO
 **/

public class ShopExecution {
    //结果状态
    private int state;

    //标识状态
    private String stateInfo;

    //店铺数量
    private int count;

    //操作的shop
    private Shop shop;

    //shop列表
    List<Shop> shopList;

    public ShopExecution() {
    }

    // 操作失败的构造器
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    // 操作成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
