package com.nckh.motelroom.controller;

import com.nckh.motelroom.dto.response.dashboard.DashboardRevenueStatDTO;
import com.nckh.motelroom.dto.response.dashboard.DashboardSummaryDTO;
import com.nckh.motelroom.service.DashboardService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Endpoint thống kê doanh thu và số giao dịch theo đơn vị: day, month hoặc year.
     * Ví dụ: GET /api/dashboard/revenue?start=2023-01-01&end=2023-12-31&groupBy=month
     */
    @ApiOperation(value = "Thống kê doanh thu và số giao dịch theo ngày/tháng/năm")
    @GetMapping("/revenue")
    public ResponseEntity<List<DashboardRevenueStatDTO>> getRevenueStatistics(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(defaultValue = "day")
            @Pattern(regexp = "day|month|year", message = "groupBy phải là day, month hoặc year")
            String groupBy) {
        List<DashboardRevenueStatDTO> stats = dashboardService.getRevenueStatistics(start, end, groupBy);
        return ResponseEntity.ok(stats);
    }

    /**
     * Endpoint thống kê tổng hợp: tổng số người dùng, giao dịch, bài viết và doanh thu.
     */
    @ApiOperation(value = "Thống kê tổng hợp số liệu trên dashboard")
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
        DashboardSummaryDTO summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }
}
