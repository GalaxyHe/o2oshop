package com.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 15:33
 * @Description TODO
 **/

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @RequestMapping(value = "/shopoperation",method = RequestMethod.GET)
    public String shopoperation(){
        return "shop/ShopOperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanage")
    public String productManagement(){
        //转发至商品管理界面
        return "shop/productmanage";
    }

    @RequestMapping(value = "/shopregister")
    public String shopregister(){
        return "shop/shopregister";
    }

}
