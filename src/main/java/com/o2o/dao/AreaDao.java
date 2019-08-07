package com.o2o.dao;

import com.o2o.pojo.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao {

    /**
      * @author He
      * @Description:返回数据库中所有区域的列表集合
      * @Date 16:55 2019/7/29
      * @Param
      * @return
      **/
    List<Area> searchall();
}
