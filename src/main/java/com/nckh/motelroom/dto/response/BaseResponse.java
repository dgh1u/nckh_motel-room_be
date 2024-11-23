package com.nckh.motelroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MinhDV
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private boolean success = false;
    private ErrorResponse error;

    public static <T> BaseItemResponse<T> successData(T data) {
        BaseItemResponse<T> response = new BaseItemResponse();
        response.setSuccess(data);
        return response;
    }

    public static <T> BaseListResponse<T> successListData(List<T> rows, Integer total) {
        BaseListResponse<T> response = new BaseListResponse();
        response.setResult(rows, total);
        return response;
    }

    public static <T> BaseResponse error(int code, String msg) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(false);
        ErrorResponse error = new ErrorResponse();
        error.setMessage(msg);
        error.setCode(code);
        baseResponse.setError(error);
        return baseResponse;
    }

    public static <T> BaseResponse error(int code, String msg, List<ErrorDetail> errors) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(false);
        ErrorResponse error = new ErrorResponse();
        error.setMessage(msg);
        error.setCode(code);
        error.setErrors(errors);
        baseResponse.setError(error);
        return baseResponse;
    }
}
