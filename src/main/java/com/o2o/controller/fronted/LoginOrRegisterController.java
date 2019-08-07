package com.o2o.controller.fronted;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.PersonInfoExecution;
import com.o2o.enums.PersonInfoStateEnum;
import com.o2o.pojo.PersonInfo;
import com.o2o.service.PersonInfoService;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author He
 * @Date 2019/8/2
 * @Time 21:20
 * @Description TODO
 **/

@Controller
@RequestMapping("/fronted")
public class LoginOrRegisterController {

    private final PersonInfoService personInfoService;

    @Autowired
    public LoginOrRegisterController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }


    @RequestMapping(value = "/userregister",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userRegister(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user;
        PersonInfoExecution personInfoExecution;
        String userStr = HttpServletRequestUtil.getString(request,"userStr");
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(userStr, PersonInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (userStr != null) {
            personInfoExecution = personInfoService.setUser(user);

            if (personInfoExecution.getState() == PersonInfoStateEnum.REGISET_SUCCESS.getState()) {
                modelMap.put("success", true);
                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "User register failed!");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty register information!");
            return modelMap;
        }

    }


    @RequestMapping(value = "/shopownerregister",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> shopOwnerRegister(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo shopowner;
        PersonInfoExecution personInfoExecution;
        String shopownerStr = HttpServletRequestUtil.getString(request,"shopownerStr");

        ObjectMapper mapper = new ObjectMapper();
        try {
            shopowner = mapper.readValue(shopownerStr, PersonInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (shopownerStr != null) {

            personInfoExecution = personInfoService.setShopOwner(shopowner);

            if (personInfoExecution.getState() == PersonInfoStateEnum.REGISET_SUCCESS.getState()) {
                modelMap.put("success", true);
                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "ShopOwner register failed!");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty register information!");
            return modelMap;
        }
    }

    @RequestMapping(value = "/loginuserandshopowner",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginUserAndShopOwner(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo personInfo1;
        PersonInfo personInfo;
        int usertype;

        String personinfoStr = HttpServletRequestUtil.getString(request, "personinfoStr");
        ObjectMapper mapper = new ObjectMapper();
        try {
            personInfo = mapper.readValue(personinfoStr, PersonInfo.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (personinfoStr != null) {
            String name = personInfo.getName();
            String password = personInfo.getPassword();
            //根据提交信息中的name和password在数据库中查找，如果返回的对象不为空，则登录成功
            personInfo1 = personInfoService.getUserorShopOwner(name,password);
            if (personInfo1 != null) {
                //把我们从数据库中根据用户名和密码查到的用户的usertype属性赋给此处的usertype
                usertype = personInfo1.getUserType();
                //区分是商家登录还是用户登录
                if (usertype == 1) {
                    request.getSession().setAttribute("user",personInfo1);
                    modelMap.put("success", true);
                    modelMap.put("usertype", 1);
                } else {
                    //将owner信息保存在session里，方便店家获取自己创建的店铺信息
                    request.getSession().setAttribute("shopowner", personInfo1);
                    modelMap.put("success", true);
                    modelMap.put("usertype", 2);
                }

                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", PersonInfoStateEnum.LOGIN_FAILED.getStateInfo());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", PersonInfoStateEnum.EMPTY.getStateInfo());
            return modelMap;
        }

    }

    @RequestMapping(value = "/userquit")
    @ResponseBody
    public Map<String,Object> userQuit(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //强制销毁session
        request.getSession().invalidate();

        modelMap.put("redirect", true);
        modelMap.put("url","/o2oshop/fronted/index");
        return modelMap;
    }

    @RequestMapping(value = "/userloginornot",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> UserloginOrNot(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if(user != null){
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
        }
        return modelMap;
    }



}
