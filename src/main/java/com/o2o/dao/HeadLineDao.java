package com.o2o.dao;

import com.o2o.pojo.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
    /**
     *
     * @return
     */
    List<HeadLine> queryHeadLine(
            @Param("headLineCondition") HeadLine headLineCondition);
}
