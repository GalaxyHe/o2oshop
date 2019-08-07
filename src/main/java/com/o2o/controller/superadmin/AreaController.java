package com.o2o.controller.superadmin;

import com.o2o.pojo.Area;
import com.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author He
 * @Date 2019/7/29
 * @Time 17:20
 * @Description TODO
 **/
@Controller
@RequestMapping("/superadmin")
public class AreaController {
    private final AreaService areaService;
    Logger logger = LoggerFactory.getLogger(AreaController.class);


    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }


    @RequestMapping(value = "/showarea", method = RequestMethod.GET)
    @ResponseBody  //自动转换为json数据返回给前端
    public Map<String, Object> showarea() {
        logger.info("====begin====");
        long startTime = System.currentTimeMillis();
        Map<String, Object> modelMap = new HashMap<>();
        List<Area> areas;

        try {
            areas = areaService.getallArea();
            modelMap.put("rows", areas);
            modelMap.put("total", areas.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.toString());
        }

        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("====end====");
        return modelMap;

    }
}
