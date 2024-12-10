package com.nckh.motelroom.exception;

import com.nckh.motelroom.constant.ErrorCodeDefs;
import com.nckh.motelroom.dto.response.BaseResponse;
import com.nckh.motelroom.dto.response.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(1)
    public BaseResponse handleException(Exception ex) {
        log.error("Exception: {}", ex);
        return BaseResponse.error(ErrorCodeDefs.ERR_OTHER, ex.getMessage());
    }

    @ExceptionHandler({AuthenticateException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(-1)
    public BaseResponse handleAuthenticateException(AuthenticateException ex) {
        log.error("AuthenticateException: {}", ex);
        return BaseResponse.error(ErrorCodeDefs.ERR_OTHER, ex.getMessage());
    }

    @ExceptionHandler({EmailException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(-1)
    public BaseResponse handleEmailException(EmailException ex) {
        log.error("EmailException: {}", ex);
        return BaseResponse.error(ErrorCodeDefs.ERR_OTHER, ex.getMessage());
    }

    @ExceptionHandler({DataExistException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(-1)
    public BaseResponse handleDataExistException(DataExistException ex) {
        log.error("DataExistException: {}", ex);
        return BaseResponse.error(ErrorCodeDefs.ERR_OTHER, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(-1)
    public BaseResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException: {}", e);
        List<FieldError> errors=e.getBindingResult().getFieldErrors();
        List<ErrorDetail> errorDetails=new ArrayList<>();
        for (FieldError fieldError:errors) {
            ErrorDetail errorDetail=new ErrorDetail();
            errorDetail.setMessage(fieldError.getDefaultMessage());
            errorDetail.setId(fieldError.getField());

            errorDetails.add(errorDetail);
        }

        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION,
                ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION),errorDetails);
    }
}
