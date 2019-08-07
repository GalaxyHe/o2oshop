package com.o2o.exceptions;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 11:04
 * @Description 自定义异常类，当进行关于商铺的业务时，如若出现错误，则抛出此异常
 **/


public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = -703489341745766939L;

    public ShopOperationException(String message) {
        super(message);
    }
}
