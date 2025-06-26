package com.expense_tracker.report_service;

import com.expense_tracker.report_service.service.ReportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReportScheduler {

    private final ReportService reportService;

    public ReportScheduler(ReportService reportService) {
        this.reportService = reportService;
    }

    @Scheduled(cron = "0 0 8 1 * *")
    public void sendReport() {
        LocalDate from = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());

        reportService.generateAndSendMonthlyReports(from.toString(), to.toString());
    }
}
