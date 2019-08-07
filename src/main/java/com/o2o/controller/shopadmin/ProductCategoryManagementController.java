package com.o2o.controller.shopadmin;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.dto.Result;
import com.o2o.enums.ProductCategoryStateEnum;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.pojo.ProductCategory;
import com.o2o.pojo.Shop;
import com.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author He
 * @Date 2019/7/31
 * @Time 19:23
 * @Description TODO
 **/

@RequestMapping("/shopadmin")
@Controller
public class ProductCategoryManagementController {
    private final ProductCategoryService productCategoryService;


    @Autowired
    public ProductCategoryManagementController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest req){
        //TODO

        Shop currentShop = (Shop) req.getSession().getAttribute("currentShop");
        List<ProductCategory> productCategoryList;
        if (currentShop != null && currentShop.getShopId() > 0) {
            productCategoryList = productCategoryService.getAllCategory(currentShop.getShopId());
            return new Result<>(true, productCategoryList);
        } else {
            ProductCategoryStateEnum productCategoryStateEnum = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<>(false, productCategoryStateEnum.getState(), productCategoryStateEnum.getStateInfo());
        }
    }

    @RequestMapping(value = "/addproductcategories", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategories(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest req) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) req.getSession().getAttribute("currentShop");
        for (ProductCategory productCategory : productCategoryList) {
            productCategory.setShopId(currentShop.getShopId());
            productCategory.setCreateTime(new Date());
        }

        if (productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId,
                        currentShop.getShopId());
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个商品类别！");
        }
        return modelMap;
    }





}
