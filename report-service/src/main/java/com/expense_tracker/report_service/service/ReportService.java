package com.expense_tracker.report_service.service;

import com.expense_tracker.report_service.client.AnalyticsClient;
import com.expense_tracker.report_service.client.UsersClient;
import com.expense_tracker.report_service.dto.ExpenseSummaryDTO;
import com.expense_tracker.report_service.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final UsersClient usersClient;
    private final AnalyticsClient analyticsClient;
    private final PdfGenerator pdfGenerator;
    private final EmailSender emailSender;

    public ReportService(UsersClient usersClient, AnalyticsClient analyticsClient, PdfGenerator pdfGenerator, EmailSender emailSender) {
        this.usersClient = usersClient;
        this.analyticsClient = analyticsClient;
        this.pdfGenerator = pdfGenerator;
        this.emailSender = emailSender;
    }

    public void generateAndSendMonthlyReports(String from, String to) {
        List<UserDTO> users = usersClient.getUsers();

        for (UserDTO user : users) {
            try {
                ExpenseSummaryDTO overview = analyticsClient.getOverview(user.email(), from, to);
                byte[] pdf = pdfGenerator.generateReport(user, overview);
                emailSender.sendReport(user.email(), pdf);

            } catch (Exception e) {
                System.err.println("Failed for user: " + user.email());
                e.printStackTrace();
            }
        }
    }

    public String generateMonthlyReports(String email, String from, String to) {

        try {
            ExpenseSummaryDTO overview = analyticsClient.getOverview(email, from, to);

            StringBuilder report = new StringBuilder();
            report.append("\n📊 Monthly Expense Report for ").append(email).append("\n");
            report.append("--------------------------------------------------\n");
            report.append("🗓️ Period: ").append(overview.period().get("startDate")).append(" to ").append(overview.period().get("endDate")).append("\n");
            report.append("💰 Total Spent: ₹").append(overview.totalSpent()).append("\n");
            report.append("🏆 Top Category: ").append(overview.topCategory().get("category"))
                    .append(" (₹").append(overview.topCategory().get("amount")).append(")\n");
            report.append("📌 Category Breakdown:\n");

            overview.categoryBreakdown().forEach((category, amount) ->
                    report.append("   - ").append(category).append(": ₹").append(amount).append("\n")
            );

            report.append("--------------------------------------------------\n");

//                // Simulate sending via email
//                System.out.println("Sending report to " + user.getEmail());
//                System.out.println(report);
            return report.toString();

        } catch (Exception e) {
            System.err.println("Failed to send report to user: " + email);
        }
        return null;
    }

    public void generateAndSendMonthlyReports(String email, String from, String to) {
        List<UserDTO> users = usersClient.getUsers();
        UserDTO user = users.stream().filter(u -> u.email().equals(email)).findFirst().orElseGet(() -> new UserDTO("", ""));
        try {
            ExpenseSummaryDTO overview = analyticsClient.getOverview(user.email(), from, to);
            byte[] pdf = pdfGenerator.generateReport(user, overview);

            // Next: send using JavaMail
            emailSender.sendReport(user.email(), pdf);

        } catch (Exception e) {
            System.err.println("Failed for user: " + user.email());
        }
    }
}
