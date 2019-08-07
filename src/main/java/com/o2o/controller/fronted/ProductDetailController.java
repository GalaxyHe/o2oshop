package com.o2o.controller.fronted;

import com.o2o.pojo.PersonInfo;
import com.o2o.pojo.Product;
import com.o2o.service.ProductService;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author He
 * @Date 2019/8/3
 * @Time 15:34
 * @Description TODO
 **/

@Controller
@RequestMapping("/fronted")
public class ProductDetailController {
    private final ProductService productService;

    @Autowired
    public ProductDetailController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        Product product;
        if (productId != -1) {
            //把productId存放进session，方便用户购买商品
            request.getSession().setAttribute("productId", productId);
            product = productService.getProductById(productId);
            modelMap.put("product", product);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }

        return modelMap;
    }

    @RequestMapping(value = "/purchaseproduct")
    @ResponseBody
    public Map<String, Object> purchaseProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = (long) request.getSession().getAttribute("productId");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (productId > 0 && user != null) {
            int effectedNum = productService.getProduct(productId);
            if (effectedNum > 0) {
                modelMap.put("success", true);
                modelMap.put("islogin", true);
                modelMap.put("message", "购买成功！");
            } else {
                modelMap.put("success", false);
                modelMap.put("islogin",false);
                modelMap.put("essMsg", "购买失败或库存不足！");
            }
        }

        return modelMap;
    }

    /*@RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> testBuying(long productId) {
        int effectedNum = productService.getProduct(productId);
        Map<String, Object> modelMap = new HashMap<>();
        boolean flag = effectedNum > 0;
        modelMap.put("success", flag);
        modelMap.put("message", flag ? "成功" : "失败");
        return modelMap;
    }*/
}
