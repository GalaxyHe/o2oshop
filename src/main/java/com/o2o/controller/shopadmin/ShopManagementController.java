package com.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ShopExecution;
import com.o2o.enums.ShopStateEnum;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.pojo.Area;
import com.o2o.pojo.PersonInfo;
import com.o2o.pojo.Shop;
import com.o2o.pojo.ShopCategory;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopSevice;
import com.o2o.util.CheckCodeUtil;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 13:13
 * @Description TODO
 **/

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    private final ShopSevice shopSevice;
    private final ShopCategoryService shopCategoryService;
    private final AreaService areaService;

    @Autowired
    public ShopManagementController(ShopSevice shopSevice, ShopCategoryService shopCategoryService, AreaService areaService) {
        this.shopSevice = shopSevice;
        this.shopCategoryService =  shopCategoryService;
        this.areaService = areaService;
    }


     /**
      * @author He
      * @Description 商铺注册
      * @Date
      * @Param
      * @return
      **/
     @RequestMapping(value = "/registershop",method = RequestMethod.POST)
     @ResponseBody
     @SuppressWarnings(value = "unchecked")
    public Map<String,Object> registerShop(HttpServletRequest request){
         Map<String, Object> modelMap = new HashMap<>();
         if (!CheckCodeUtil.checkVerifyCode(request)) {
             modelMap.put("success", false);
             modelMap.put("errMsg", "验证码错误！");
             return modelMap;
         }
         Shop shop;
         String regshopStr = HttpServletRequestUtil.getString(request, "regshopStr");
         ObjectMapper objectMapper = new ObjectMapper();
         try {
             shop = objectMapper.readValue(regshopStr, Shop.class);
         } catch (Exception e) {
             modelMap.put("success", false);
             modelMap.put("errMsg", e.getMessage());
             return modelMap;
         }

         //判断提交的表单中的上传文件
         MultipartHttpServletRequest multipartRequest;
         CommonsMultipartFile shopImg;
         CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                 request.getSession().getServletContext());
         if (multipartResolver.isMultipart(request)) {
             multipartRequest = (MultipartHttpServletRequest) request;
             shopImg = (CommonsMultipartFile) multipartRequest
                     .getFile("regshopImg");
         } else {
             modelMap.put("success", false);
             modelMap.put("errMsg", "上传图片不能为空");
             return modelMap;
         }

         //注册店铺
         if (regshopStr != null && shopImg != null) {
             PersonInfo shopowner = (PersonInfo) request.getSession().getAttribute("shopowner");
             shop.setOwner(shopowner);
             ShopExecution shopExecution;

             try {
                 shopExecution = shopSevice.setShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                 if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                     modelMap.put("success", true);
                     //用户可以操作的所有店铺
                     List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                     if (shopList == null || shopList.size() == 0) {
                         shopList = new ArrayList<>();
                     }
                     shopList.add(shopExecution.getShop());
                     request.getSession().setAttribute("shopList", shopList);
                 } else {
                     modelMap.put("success", false);
                     modelMap.put("errMsg", shopExecution.getStateInfo());
                 }
             } catch (ShopOperationException | IOException e) {
                 modelMap.put("success", false);
                 modelMap.put("errMsg", e.getMessage());
             }

             return modelMap;
         } else {
             modelMap.put("success", false);
             modelMap.put("errMsg", "Please input your shop message!");
             return modelMap;
         }
    }



     /**
      * @author He
      * @Description 展示注册商铺时所需信息
      * @Date
      * @Param
      * @return
      */
     @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
     @ResponseBody
     public Map<String,Object> getShopInitInfo(){
         Map<String, Object> modelMap = new HashMap<>();
         List<ShopCategory> shopCategoryList;
         List<Area> areaList;
         try {
             shopCategoryList = shopCategoryService.getAll(new ShopCategory());
             areaList = areaService.getallArea();
             modelMap.put("shopCategoryList", shopCategoryList);
             modelMap.put("areaList", areaList);
             modelMap.put("success", true);
         } catch (Exception e) {
             modelMap.put("success", false);
             modelMap.put("errMsg", e.getMessage());
         }
         return modelMap;
     }

    /**
     * @author He
     * @Description 展示自己店铺的信息
     * @Date
     * @Param
     * @return
     */
    @RequestMapping(value = "/getmyselfshop")
    @ResponseBody
    public Map<String,Object> getmyselfshop(HttpServletRequest req){
        HashMap<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(req, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopSevice.getByShopId(shopId);
                List<Area> areaList = areaService.getallArea();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "get myself shop information failed!" + e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId！");
        }
        return modelMap;
    }

    /**
     * @author He
     * @Description 修改店铺信息
     * @Date
     * @Param request
     * @return Map
     */
    @RequestMapping(value = "/modifyshopinfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyshopInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        if (!CheckCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }
        Shop shop;
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //判断提交的表单中的上传文件
        MultipartHttpServletRequest multipartRequest;
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest
                    .getFile("shopImg");
        }

        //修改店铺信息
        if (shopStr != null && shop.getShopId() != null) {
            ShopExecution shopExecution;

            try {
                if(shopImg == null){
                    shopExecution = shopSevice.modifyShopInfo(shop,null,null);
                }else {
                    shopExecution = shopSevice.modifyShopInfo(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                }

                if (Objects.requireNonNull(shopExecution).getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (ShopOperationException | IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Please input your shopId!");
            return modelMap;
        }
    }

    /**
     * @author He
     * @Description 分页展示自己创建的店铺信息
     * @Date
     * @Param
     * @return
     */
    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest req){
        Map<String,Object> modelMap = new HashMap<>();
        PersonInfo shopowner;
        //从session里获取我们登录的商家的信息
        shopowner = (PersonInfo) req.getSession().getAttribute("shopowner");
        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(shopowner);
            ShopExecution shopExecution = shopSevice.getShopList(shopCondition,0,100);
            modelMap.put("shopList",shopExecution.getShopList());
            modelMap.put("shopowner",shopowner);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    /**
     * @author He
     * @Description
     * @Date
     * @Param
     * @return
     */
    @RequestMapping(value = "/getshopmanagementinfo")
    @ResponseBody
    public Map<String,Object> getShopManagementInfo(HttpServletRequest req){
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(req, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = req.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            req.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }








}
