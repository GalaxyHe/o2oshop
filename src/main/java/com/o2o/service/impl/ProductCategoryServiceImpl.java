package com.o2o.service.impl;

import com.o2o.dao.ProductCategoryDao;
import com.o2o.dao.ProductDao;
import com.o2o.dto.ProductCategoryExecution;
import com.o2o.enums.ProductCategoryStateEnum;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.pojo.ProductCategory;
import com.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/31
 * @Time 19:20
 * @Description TODO
 **/
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryDao productCategoryDao;

    private final ProductDao productDao;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryDao productCategoryDao, ProductDao productDao) {
        this.productCategoryDao = productCategoryDao;
        this.productDao = productDao;
    }


    @Override
    public List<ProductCategory> getAllCategory(long shopId) {
        return productCategoryDao.queryProducetCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao
                        .batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别失败");
                } else {

                    return new ProductCategoryExecution(
                            ProductCategoryStateEnum.SUCCESS);
                }

            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error: "
                        + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(
                    ProductCategoryStateEnum.INNER_ERROR);
        }

    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException {
        try {
            int res = productDao.updateProductCategoryToNull(productCategoryId);
            if (res < 0) {
                throw new ProductCategoryOperationException("商品类别更新失败！");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("delete productcategory failed!" + e.getMessage());
        }

        try {
            int res = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (res <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败！");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("Delete category id failed!" + e.getMessage());
        }
    }
}
