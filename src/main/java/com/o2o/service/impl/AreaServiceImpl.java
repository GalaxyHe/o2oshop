package com.o2o.service.impl;

import com.o2o.dao.AreaDao;
import com.o2o.pojo.Area;
import com.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/29
 * @Time 17:12
 * @Description TODO
 **/
@Service
public class AreaServiceImpl implements AreaService {
    private final AreaDao areaDao;

    @Autowired
    public AreaServiceImpl(AreaDao areaDao) {
        this.areaDao = areaDao;
    }


    /**
     * @author He
     * @Description 获取所有区域
     * @Date 17:13 2019/7/29
     * @Param
     * @return
     **/
    @Override
    public List<Area> getallArea() {
        return areaDao.searchall();
    }
}
