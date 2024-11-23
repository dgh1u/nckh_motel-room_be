package com.nckh.motelroom.dto.response;

import lombok.Data;

/**
 * @author MinhDV
 */

@Data
public class DataList<T> {
    private Integer total = 0;
    private T items;
}
