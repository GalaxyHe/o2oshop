package com.o2o.controller.fronted;

import com.o2o.dto.ShopExecution;
import com.o2o.pojo.Area;
import com.o2o.pojo.Shop;
import com.o2o.pojo.ShopCategory;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopSevice;
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

/**
 * @author He
 * @Date 2019/8/1
 * @Time 21:14
 * @Description TODO
 **/
@Controller
@RequestMapping("/fronted")
public class shopListController {
    private final ShopCategoryService shopCategoryService;
    private final AreaService areaService;
    private final ShopSevice shopSevice;

    @Autowired
    public shopListController(ShopCategoryService shopCategoryService, AreaService areaService, ShopSevice shopSevice) {
        this.shopCategoryService = shopCategoryService;
        this.areaService = areaService;
        this.shopSevice = shopSevice;
    }

    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getAll(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            try {
                shopCategoryList = shopCategoryService.getAll(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);

        List<Area> areaList;
        try {
            areaList = areaService.getallArea();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }


    @RequestMapping(value = "/listshops",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex > -1 && pageSize > -1) {
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            ShopExecution shopExecution = shopSevice.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("count", shopExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize Or pageIndex");
        }
        return modelMap;
    }

    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1) {
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }

        if (shopCategoryId != -1) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }

        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
