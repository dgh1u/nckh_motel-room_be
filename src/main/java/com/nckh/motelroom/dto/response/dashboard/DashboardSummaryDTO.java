package com.nckh.motelroom.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDTO {
    // Tổng số người dùng
    private long totalUsers;
    // Tổng số giao dịch (tất cả giao dịch trong PaymentHistory)
    private long totalPayments;
    // Tổng số bài viết (dựa trên PostRepository)
    private long totalPosts;
    // Tổng doanh thu = tổng amount của các giao dịch có success = true
    private long totalRevenue;
}
