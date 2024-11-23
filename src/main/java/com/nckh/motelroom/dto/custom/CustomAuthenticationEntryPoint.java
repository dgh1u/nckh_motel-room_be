package com.nckh.motelroom.dto.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nckh.motelroom.constant.ErrorCodeDefs;
import com.nckh.motelroom.dto.response.BaseResponse;
import com.nckh.motelroom.dto.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ErrorCodeDefs.ERR_PERMISSION_INVALID);
        errorResponse.setMessage(ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_PERMISSION_INVALID));

        response.setStatus(errorResponse.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        BaseResponse apiResponse = BaseResponse.builder()
                .success(false)
                .error(errorResponse)
                .build();

        ObjectMapper objectMapper=new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
