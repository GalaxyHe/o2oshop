package com.o2o.exceptions;

/**
 * @author He
 * @Date 2019/7/31
 * @Time 20:41
 * @Description TODO
 **/

public class ProductCategoryOperationException extends RuntimeException {

    public static final long serialVersionUID = 7030745766939L;

    public ProductCategoryOperationException(String message) {
        super(message);
    }
}
