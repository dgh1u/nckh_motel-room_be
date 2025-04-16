package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.response.dashboard.DashboardRevenueStatDTO;
import com.nckh.motelroom.dto.response.dashboard.DashboardSummaryDTO;

import java.util.List;

public interface DashboardService {
    /**
     * Lấy thống kê doanh thu và số giao dịch theo đơn vị: day, month hoặc year.
     * Tham số start và end có định dạng chuỗi (ví dụ: yyyy-MM-dd) để lọc theo thời gian dựa trên trường transactionDateTime.
     */
    List<DashboardRevenueStatDTO> getRevenueStatistics(String start, String end, String groupBy);

    /**
     * Lấy thống kê tổng hợp: tổng số người dùng, tổng số giao dịch, tổng số bài viết, tổng doanh thu.
     */
    DashboardSummaryDTO getDashboardSummary();
}
