package com.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.enums.ProductStateEnum;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.pojo.Product;
import com.o2o.pojo.ProductCategory;
import com.o2o.pojo.ProductImg;
import com.o2o.pojo.Shop;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.util.CheckCodeUtil;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 10:47
 * @Description TODO
 **/

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    //允许最多一次上传图片数
    private static final int IMAGE_MAX_COUNT = 6;


    @Autowired
    public ProductManagementController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }




    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CheckCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        ObjectMapper mapper = new ObjectMapper();
        Product product;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ImageHolder thumbnail;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = getImageHolder((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (product != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService
                    .getAllCategory(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }



    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        Map<String, Object> modelMap = new HashMap<>();
        if (!statusChange && !CheckCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product;
        String productStr = HttpServletRequestUtil.getString(request,
                "productStr");
        ImageHolder thumbnail;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = getImageHolder((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.modifyProduct(product,
                        thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
                && (currentShop.getShopId() != null)) {
            long productCategoryId = HttpServletRequestUtil.getLong(request,
                    "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request,
                    "productName");
            Product productCondition = compactProductCondition(
                    currentShop.getShopId(), productCategoryId, productName);
            ProductExecution pe = productService.getProductList(
                    productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }



    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
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
        return productCondition;
    }

    private ImageHolder getImageHolder(MultipartHttpServletRequest request, List<ImageHolder> productImgList)
            throws IOException {

        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;
        multipartRequest = request;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        thumbnail = new ImageHolder(Objects.requireNonNull(commonsMultipartFile).getOriginalFilename(),
                commonsMultipartFile.getInputStream());
        for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
                    .getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                break;
            }
        }
        return thumbnail;
    }
}
