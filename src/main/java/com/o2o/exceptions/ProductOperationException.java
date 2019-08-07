package com.o2o.exceptions;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 9:36
 * @Description TODO
 **/

public class ProductOperationException extends RuntimeException {
    public ProductOperationException(String message) {
        super(message);
    }
}
