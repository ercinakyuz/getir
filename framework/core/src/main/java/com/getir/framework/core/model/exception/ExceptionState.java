package com.getir.framework.core.model.exception;

public enum ExceptionState {
    UNKNOWN,   //500
    INVALID, // 400
    AUTHORIZATION_FAILED,  //401
    DOES_NOT_EXIST, //404
    ALREADY_EXIST,  //409
    PRE_CONDITION_REQUIRED, //428
    PRE_CONDITION_FAILED,    //412
    UNPROCESSABLE,   //422
}
