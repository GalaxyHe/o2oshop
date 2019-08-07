package com.o2o.controller.fronted;

import com.o2o.dto.ProductExecution;
import com.o2o.pojo.Product;
import com.o2o.pojo.ProductCategory;
import com.o2o.pojo.Shop;
import com.o2o.service.*;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author He
 * @Date 2019/8/2
 * @Time 10:46
 * @Description TODO
 **/

@Controller
@RequestMapping("/fronted")
public class ShopDetailController {
    private final ShopSevice shopSevice;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ShopDetailController(ShopSevice shopSevice, ProductService productService,
                                ProductCategoryService productCategoryService) {
        this.shopSevice = shopSevice;
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop;
        List<ProductCategory> productCategoryList;
        if (shopId != -1) {
            shop = shopSevice.getByShopId(shopId);
            productCategoryList = productCategoryService.getAllCategory(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId!");
        }
        return modelMap;

    }

    @RequestMapping(value = "listproductsbyshop",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listProductsByShop(HttpServletRequest req) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(req, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(req, "pageSize");
        long shopId = HttpServletRequestUtil.getLong(req, "shopId");

        if (pageIndex > -1 && pageSize > -1) {
            long productCategoryId = HttpServletRequestUtil.getLong(req, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(req, "productName");
            Product productCondition = compactProduct4Search(shopId, productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize!");
        }
        return modelMap;
    }

    private Product compactProduct4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if (productName != null) {
            productCondition.setProductName(productName);
        }

        productCondition.setEnableStatus(1);
        return productCondition;
    }


}
