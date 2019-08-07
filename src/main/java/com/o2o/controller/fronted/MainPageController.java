package com.o2o.controller.fronted;

import com.o2o.pojo.HeadLine;
import com.o2o.pojo.ShopCategory;
import com.o2o.service.HeadLineService;
import com.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 20:28
 * @Description TODO
 **/
@Controller
@RequestMapping("/fronted")
public class MainPageController {
    private final HeadLineService headLineService;
    private final ShopCategoryService shopCategoryService;

    @Autowired
    public MainPageController(HeadLineService headLineService, ShopCategoryService shopCategoryService) {
        this.headLineService = headLineService;
        this.shopCategoryService = shopCategoryService;
    }


    /**
      * @author He
      * @Description 初始化前端展示系统的主页信息，包括获取一级店铺类别一级头条列表
      * @Date
      * @Param
      * @return
      */
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPageInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList;
        try {
            shopCategoryList = shopCategoryService.getAll(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        List<HeadLine> headLineList;
        try {
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList", headLineList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

}
