package com.nckh.motelroom.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardRevenueStatDTO {
    // Khóa nhóm: ngày/tháng/năm (vd: "2023-05-28" hoặc "2023-05" hoặc "2023")
    private String groupKey;
    // Tổng số giao dịch (không phân biệt thành công hay không)
    private long transactionCount;
    // Tổng doanh thu = tổng amount của các giao dịch có success = true
    private long totalRevenue;
}
