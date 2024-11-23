package com.nckh.motelroom.constant;

public class ErrorCodeDefs {
    public static final int ERR_OK = 0;
    public static final int ERR_VALIDATION = ERR_OK + 1;
    public static final int ERR_OBJECT_NOT_FOUND = ERR_OK + 3;
    public static final int ERR_UNAUTHORIZED = ERR_OK + 5;
    public static final int ERR_TOKEN_INVALID = ERR_OK + 6;
    public static final int ERR_REFRESH_TOKEN_INVALID = ERR_OK + 7;
    public static final int ERR_HEADER_TOKEN_REQUIRED = ERR_OK + 8;
    public static final int ERR_OTHER = ERR_OK + 9;

    public static final int ERR_BAD_REQUEST = 400;
    public static final int ERR_PERMISSION_INVALID = 401;
    public static final int ERR_ACCESS_FORBIDDEN = 403;
    public static final int ERR_VALIDATE_ERROR_OCCURED = 422;

    public static final int ERR_SERVER_ERROR = 500;

    public static String getMessage(int errorCode) {
        switch (errorCode) {
            case ERR_OK:
                return "Thành công";
            case ERR_VALIDATION:
                return "Dữ liệu/Tham số không hợp lệ";
            case ERR_OBJECT_NOT_FOUND:
                return "Không tìm thấy dữ liệu";
            case ERR_UNAUTHORIZED:
                return "Không được truy cập hệ thống do chưa xác thực, hoặc xác thực không thành công";
            case ERR_TOKEN_INVALID:
                return "Token không đúng hoặc đã hết hạn";
            case ERR_REFRESH_TOKEN_INVALID:
                return "Refresh token không đúng hoặc đã hết hạn";
            case ERR_HEADER_TOKEN_REQUIRED:
                return "Header token is required";
            case ERR_BAD_REQUEST:
                return "Tham số không hợp lệ";
            case ERR_PERMISSION_INVALID:
                return "Bạn không có quyền truy cập chức năng này";
            case ERR_ACCESS_FORBIDDEN:
                return "Truy cập không hợp lệ";
            case ERR_VALIDATE_ERROR_OCCURED:
                return "Khi tạo một đối tượng, đã xảy ra lỗi xác thực";
            case ERR_SERVER_ERROR:
                return "Lỗi không xác định";
            case ERR_OTHER:
                return "Các lỗi khác";
            default:
                return "Lỗi không xác định";
        }
    }
}
