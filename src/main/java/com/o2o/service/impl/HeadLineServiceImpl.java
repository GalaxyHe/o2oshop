package com.o2o.service.impl;

import com.o2o.dao.HeadLineDao;
import com.o2o.pojo.HeadLine;
import com.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 20:26
 * @Description TODO
 **/
@Service
public class HeadLineServiceImpl implements HeadLineService {
    private final HeadLineDao headLineDao;

    @Autowired
    public HeadLineServiceImpl(HeadLineDao headLineDao) {
        this.headLineDao = headLineDao;
    }

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
