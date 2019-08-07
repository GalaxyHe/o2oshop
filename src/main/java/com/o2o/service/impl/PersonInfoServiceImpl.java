package com.o2o.service.impl;

import com.o2o.dao.PersonInfoDao;
import com.o2o.dto.PersonInfoExecution;
import com.o2o.enums.PersonInfoStateEnum;
import com.o2o.exceptions.PersonInfoOperationException;
import com.o2o.pojo.PersonInfo;
import com.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author He
 * @Date 2019/8/2
 * @Time 21:03
 * @Description TODO
 **/

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    @Transactional
    public PersonInfoExecution setUser(PersonInfo userCondition) {
        if (userCondition == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
        }

        try {
            userCondition.setEnableStatus(1);
            userCondition.setUserType(1);
            userCondition.setCreateTime(new Date());

            int effectnNum = personInfoDao.insertUser(userCondition);
            if (effectnNum <= 0) {
                throw new PersonInfoOperationException("插入数据库失败!");
            } else {
                return new PersonInfoExecution(PersonInfoStateEnum.REGISET_SUCCESS, userCondition);
            }

        } catch (Exception e) {
            throw new PersonInfoOperationException("用户注册失败！");
        }
    }

    @Override
    @Transactional
    public PersonInfoExecution setShopOwner(PersonInfo shopownerCondition) {
        if (shopownerCondition == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
        }

        try {

            shopownerCondition.setEnableStatus(1);
            //商户注册，则usertype为2
            shopownerCondition.setUserType(2);
            shopownerCondition.setCreateTime(new Date());

            int effectNum = personInfoDao.insertShopOwner(shopownerCondition);
            if (effectNum <= 0) {
                throw new PersonInfoOperationException("插入数据库失败!");
            } else {
                return new PersonInfoExecution(PersonInfoStateEnum.REGISET_SUCCESS, shopownerCondition);
            }
        } catch (Exception e) {
            throw new PersonInfoOperationException("商户注册失败！");
        }
    }

    @Override
    public PersonInfo getUserorShopOwner(String name, String password) {
        if(name == null || password == null){
            throw new PersonInfoOperationException("用户名或密码为空！");
        }

        return personInfoDao.findByUsernameAndPassword(name,password);
    }
}
