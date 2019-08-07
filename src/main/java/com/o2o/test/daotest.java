package com.o2o.test;

import com.o2o.dao.*;
import com.o2o.pojo.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.UIResource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author He
 * @Date 2019/7/29
 * @Time 17:06
 * @Description TODO
 **/

public class daotest extends basetest {

    
    

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Autowired
    private PersonInfoDao personInfoDao;
    @Autowired
    private ProductDao productDao;

    @Test
    @Ignore
    public void testarea(){
        List<Area> areaList = areaDao.searchall();
        for(Area area:areaList){
            System.out.println(area);
        }
    }


    @Test
    @Ignore
    public void testshop(){
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        Area area = new Area();
        PersonInfo owner = new PersonInfo();

        owner.setUserId(2L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("零食铺子");
        shop.setShopDesc("鸡你实在太美！");
        shop.setShopAddr("A#223");
        shop.setPhone("12345561");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核...");
        int effectedNum = shopDao.insertShop(shop);
        System.out.println(effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop() {
        // shop_id 不可更新 personInfo不可更新
        Shop shop = new Shop();

        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        shop.setShopId(30L);

        // 将area_id更新成2
        area.setAreaId(2);
        // 为了防止因外键约束，导致更新异常，同时也能验证更新方法没有问题
        // 新增一条测试数据将shopCategoryId更新为2
        shopCategory.setShopCategoryId(2L);

        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("ArtisanUP");
        shop.setShopDesc("ArtisanDescUP");
        shop.setShopAddr("NanJingUP");
        shop.setPhone("123456UP");
        shop.setShopImg("/xxx/xxx/UP");
        shop.setPriority(66);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("Waring UP");

        int effectNum = shopDao.updateShop(shop);

        Assert.assertEquals(effectNum, 1);
    }

    @Test
    public void testShopcategoryList(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        Assert.assertEquals(2,shopCategoryList.size());
    }

    @Test
    public void testqueryshopbyId(){
        long shopId = 60L;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop);
    }


    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int shopCount = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表："+shopList.size());
        System.out.println(shopCount);
    }


    @Test
    @Ignore
    public void testQueryProductCategory(){
        long shopId = 30L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProducetCategoryList(shopId);
        for (ProductCategory productCategory : productCategoryList) {
            System.out.println(productCategory);
        }

    }

    @Test
    @Ignore
    public void testBatchInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("牙膏");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(31L);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("泡面");
        productCategory1.setPriority(2);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(31L);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory1);
        int res = productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println(res);

    }

    @Test
    @Ignore
    public void testDeleteProductCategory() {
        long shopId = 31L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProducetCategoryList(shopId);
        for (ProductCategory productCategory : productCategoryList) {
            if ("牙膏".equals(productCategory.getProductCategoryName()) || "泡面".equals(productCategory.getProductCategoryName())) {
                int res = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
                System.out.println(res);
            }
        }
    }

    @Test
    public void testqueryProductImgList(){
        List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
        System.out.println(productImgList);
    }



    @Test
    public void testinsertshopowner(){
        PersonInfo shopowner = new PersonInfo();
        shopowner.setName("蔡徐坤");
        shopowner.setPassword("123456");
        shopowner.setCreateTime(new Date());
        shopowner.setProfileImg(null);
        shopowner.setEmail("1308902047@qq.com");
        shopowner.setGender("女");
        shopowner.setEnableStatus(1);
        shopowner.setUserType(2);
        int res = personInfoDao.insertShopOwner(shopowner);
        System.out.println(res);
    }

    @Test
    public void testcount(){
        Shop shop = new Shop();
        shop.setShopName("威水");
        int count = shopDao.queryShopCount(shop);
        System.out.println(count);
    }

    @Test
    public void testgetproductbyId(){
        long productId = 15L;
        Product product = productDao.queryProductByProductId(productId);
        System.out.println(product.getInventory());

    }



    @Test
    public void searchstock(){
        long productId = 15L;
        Product product = productDao.searchProductInventory(productId);
        System.out.println(product.getInventory());
    }

    @Test
    public void testbuy(){

        int effectedNum = productDao.decreaseProductInventory(15L);
        System.out.println(effectedNum);

    }


}
