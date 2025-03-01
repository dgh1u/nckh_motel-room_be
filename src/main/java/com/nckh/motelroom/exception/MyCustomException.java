package com.nckh.motelroom.exception;

public class MyCustomException extends RuntimeException{
    public MyCustomException(String mes){
        super(mes);
    }
}
