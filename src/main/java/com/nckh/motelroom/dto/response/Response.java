package com.nckh.motelroom.dto.response;

import lombok.Data;

@Data
public class Response<T> {
    private String message;
    private T data;
    private int status;

    public Response(String message, T data, int status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
