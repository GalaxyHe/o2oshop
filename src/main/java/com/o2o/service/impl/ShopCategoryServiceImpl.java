package com.o2o.service.impl;

import com.o2o.dao.ShopCategoryDao;
import com.o2o.pojo.ShopCategory;
import com.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 17:32
 * @Description TODO
 **/

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryDao shopCategoryDao;

    @Autowired
    public ShopCategoryServiceImpl(ShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }


    @Override
    public List<ShopCategory> getAll(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
