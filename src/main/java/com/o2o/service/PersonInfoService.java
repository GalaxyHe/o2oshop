package com.o2o.service;

import com.o2o.dto.PersonInfoExecution;
import com.o2o.pojo.PersonInfo;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoService {

    //普通用户注册
    PersonInfoExecution setUser(PersonInfo userCondition);

    //商家注册
    PersonInfoExecution setShopOwner(PersonInfo shopownerCondition);

    //登录判断
    PersonInfo getUserorShopOwner( String name, String password);

}
