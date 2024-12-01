package com.nckh.motelroom.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class BaseListResponse<T> extends BaseResponse {

    private DataList data;

    public void setResult(List<T> rows, Integer total) {
        if (rows != null) {
            super.setSuccess(true);
            data = new DataList();
            data.setItems(rows);
            data.setTotal(total);
        }
    }

}
