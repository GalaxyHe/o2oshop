package com.o2o.service.impl;

import com.o2o.dao.ProductDao;
import com.o2o.dao.ProductImgDao;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductCategoryExecution;
import com.o2o.dto.ProductExecution;
import com.o2o.enums.PersonInfoStateEnum;
import com.o2o.enums.ProductStateEnum;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.pojo.Product;
import com.o2o.pojo.ProductImg;
import com.o2o.service.ProductService;
import com.o2o.util.ImageUtil;
import com.o2o.util.PageCalculator;
import com.o2o.util.PathUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 9:33
 * @Description TODO
 **/
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductImgDao productImgDao;
    private static final int FAILED = 0;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductImgDao productImgDao) {
        this.productDao = productDao;
        this.productImgDao = productImgDao;
    }


    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imgHolderlist)
            throws ProductOperationException {
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            //给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架的状态
            product.setEnableStatus(1);
            //若商品缩略图不为空则添加
            if(thumbnail != null){
                addThumbnail(product,thumbnail);
            }
            try{
                //创建商品信息
                int res = productDao.insertProduct(product);
                if(res <= 0){
                    throw new ProductOperationException("创建商品信息失败！");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品失败！"+e.getMessage());
            }
            //若商品详情图不为空则添加
            if(imgHolderlist != null && imgHolderlist.size() > 0){
                addProductImgList(product,imgHolderlist);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }


    /**
     * @author He
     * @Description 根据商品的id查询商品
     * @Date
     * @Param
     * @return
     */
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductByProductId(productId);
    }


    /**
     * @author He
     * @Description 修改商品信息
     * @Date
     * @Param
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT)
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImglist)
            throws ProductOperationException {

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }

            if (productImglist != null && productImglist.size() > 0) {
                deleteProductImgAll(product.getProductId());
                addProductImgList(product, productImglist);
            }

            try {
                int res = productDao.updateProduct(product);
                if (res <= 0) {
                    throw new ProductOperationException("更新商品信息失败！");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败！" + e.getMessage());
            }

        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    /**
     * @author He
     * @Description 购买商品的业务
     * @Date
     * @Param
     * @return
     */
    @Override
    @Transactional
    public int getProduct(long productId) {
        Product product = productDao.searchProductInventory(productId);
        int stock = product.getInventory();
        if (stock > 0) {
            try {
                int effectedNum = productDao.decreaseProductInventory(productId);
                if (effectedNum > 0) {
                    return effectedNum;
                }
            } catch (ProductOperationException e) {
                throw new ProductOperationException("购买时出错！" + e.getMessage());
            }
        } else {
            return FAILED;
        }

        return FAILED;
    }


    /**
      * @author He
      * @Description 添加缩略图
      * @Date
      * @Param
      * @return
      */
    private void addThumbnail(Product product,ImageHolder thumbnail){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumnbaddr = ImageUtil.generateThumbnail(thumbnail.getImage(),thumbnail.getImageName(),dest);
        product.setImgAddr(thumnbaddr);
    }

    /**
     * @author He
     * @Description 批量添加图片
     * @Date
     * @Param
     * @return
     */
    private void addProductImgList(Product product,List<ImageHolder> productImgHolder){
        //获取图片的存储路径，直接存放到响应店铺的文件夹下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHolder imageHolder : productImgHolder) {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        if(productImgList.size() > 0){
            try{
                int res = productImgDao.batchInsertProductImg(productImgList);
                if(res <= 0){
                    throw new ProductOperationException("创建商品详情图失败！");
                }

            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图失败 ！"+e.getMessage());
            }
        }
    }

    /**
     * @author He
     * @Description 删除某个商品的所有详情图
     * @Date
     * @Param
     * @return
     */
    private void deleteProductImgAll(long productId){
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            //删除磁盘中图片信息
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }

        //删除数据库中的信息
        productImgDao.deleteProductImgByProductId(productId);

    }


}
