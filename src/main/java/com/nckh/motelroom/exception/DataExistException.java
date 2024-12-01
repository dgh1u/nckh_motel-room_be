package com.nckh.motelroom.exception;

import lombok.Data;

@Data
public class DataExistException extends RuntimeException{
    public DataExistException(String message){
        super(message);
    }
}
