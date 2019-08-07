package com.o2o.exceptions;

/**
 * @author He
 * @Date 2019/8/2
 * @Time 21:12
 * @Description TODO
 **/

public class PersonInfoOperationException extends RuntimeException {
    public PersonInfoOperationException(String message) {
        super(message);
    }
}
