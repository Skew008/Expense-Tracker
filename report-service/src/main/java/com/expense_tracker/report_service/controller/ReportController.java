package com.expense_tracker.report_service.controller;

import com.expense_tracker.report_service.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("preview/{email}")
    public ResponseEntity<String> getPreviewReport(@PathVariable String email, @RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(reportService.generateMonthlyReports(email, from, to));
    }

    @PostMapping("send/{email}")
    public ResponseEntity<Object> sendReport(@PathVariable String email, @RequestParam String from, @RequestParam String to) {
        reportService.generateAndSendMonthlyReports(email, from, to);
        return ResponseEntity.ok().build();
    }
}
