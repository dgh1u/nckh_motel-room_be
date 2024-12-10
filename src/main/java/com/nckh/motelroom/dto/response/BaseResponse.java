package com.nckh.motelroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private boolean success = false;
    private ErrorResponse error;

    public static <T> ResponseEntity<?> successData(T data) {
        BaseItemResponse<T> response = new BaseItemResponse<>();
        response.setSuccess(data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<?> successListData(List<T> rows, Integer total) {
        BaseListResponse<T> response = new BaseListResponse<>();
        response.setResult(rows, total);
        return ResponseEntity.ok(response);
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
