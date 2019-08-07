package com.o2o.test;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.PersonInfoExecution;
import com.o2o.dto.ProductExecution;
import com.o2o.dto.ShopExecution;
import com.o2o.enums.ProductStateEnum;
import com.o2o.enums.ShopStateEnum;
import com.o2o.pojo.*;
import com.o2o.service.*;
import com.o2o.service.impl.ProductServiceImpl;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author He
 * @Date 2019/7/29
 * @Time 17:13
 * @Description TODO
 **/

public class servicetest extends basetest{
    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopSevice shopSevice;

    @Autowired
    private ProductService productService;

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private PersonInfoService personInfoService;

    @Test
    public void areatest(){
        List<Area> list = areaService.getallArea();
        for(Area area:list){
            System.out.println(area);
        }
    }

    @Test
    public void shoptest() throws FileNotFoundException {
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        Area area = new Area();
        PersonInfo owner = new PersonInfo();

        owner.setUserId(1L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("庆丰包子铺2");
        shop.setShopDesc("very good！");
        shop.setShopAddr("B#220");
        shop.setPhone("1222345561");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("通过");

        File shopImg = new File("K:\\VirDir\\lunhuiyan.jpg");
        FileInputStream fileInputStream = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopSevice.setShop(shop,fileInputStream,shopImg.getName());
        System.out.println(shopExecution.getState());
    }


    @Test
    public void testmodifyshop() throws Exception{
        Shop shop = new Shop();
        shop.setShopId(30L);
        shop.setShopName("茶颜悦色");
        File shopImg = new File("K:\\VirDir\\lunhuiyan.jpg");
        InputStream in = new FileInputStream(shopImg);
        String filename = shopImg.getName();
        ShopExecution shopExecution = shopSevice.modifyShopInfo(shop, in, filename);
        System.out.println(shopExecution.getShop().getShopName());
    }


    @Test
    public void testgetproductbyid(){
        long productId = 1L;
        Product product = productService.getProductById(productId);
        System.out.println(product);
    }

    @Test
    public void testmodifyproduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(57L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(18L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("云烟");
        product.setProductDesc("精品香烟");
        File thumbnailFile = new File("K:\\VirDir\\lunhuiyan.jpg");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),inputStream);
        File productImg1 = new File("K:\\VirDir\\lunhuiyan.jpg");
        InputStream inputStream1 = new FileInputStream(productImg1);
        File productImg2 = new File("K:\\VirDir\\cxk.png");
        InputStream inputStream2 = new FileInputStream(productImg2);
        List<ImageHolder> imageHolderList = new ArrayList<>();
        imageHolderList.add(new ImageHolder(productImg1.getName(),inputStream1));
        imageHolderList.add(new ImageHolder(productImg2.getName(),inputStream2));
        ProductExecution productExecution = productService.modifyProduct(product,thumbnail,imageHolderList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
    }

    @Test
    public void testheadline() throws IOException {
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine> lineList = headLineService.getHeadLineList(headLine);
        for (HeadLine line : lineList) {
            System.out.println(line);
        }

    }

    @Test
    public void testsetUser(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("孙艺兴");
        personInfo.setPassword("123456");
        personInfo.setCreateTime(new Date());
        personInfo.setProfileImg(null);
        personInfo.setEmail("1308902047@qq.com");
        personInfo.setGender("男");
        personInfo.setEnableStatus(1);
        personInfo.setUserType(1);
        PersonInfoExecution personInfoExecution = personInfoService.setUser(personInfo);
        System.out.println(personInfoExecution);
    }


    @Test
    public void testlogin(){
        String name = "syx123";
        String password = "123456";
        PersonInfo user = personInfoService.getUserorShopOwner(name, password);
        System.out.println(user);

    }

    @Test
    public void testgetprductbyId(){
        Product product = new Product();
        product.setProductId(1L);
        Product product1 = productService.getProductById(product.getProductId());
        System.out.println(product1);

    }
}
