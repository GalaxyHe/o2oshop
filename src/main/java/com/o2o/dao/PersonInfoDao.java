package com.o2o.dao;

import com.o2o.pojo.PersonInfo;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDao {



     /**
      * @author He
      * @Description 用户注册
      * @Date
      * @Param
      * @return
      */

     int insertUser(PersonInfo user);

    /**
     * @author He
     * @Description 判断用户登录
     * @Date
     * @Param
     * @return
     */
    PersonInfo findByUsernameAndPassword(@Param("name") String name, @Param("password") String password);


    /**
     * @author He
     * @Description 商家注册
     * @Date
     * @Param
     * @return
     */
    int insertShopOwner(PersonInfo owner);


}
