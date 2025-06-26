package com.expense_tracker.report_service.service;

import com.expense_tracker.report_service.dto.ExpenseSummaryDTO;
import com.expense_tracker.report_service.dto.UserDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
public class PdfGenerator {

    public byte[] generateReport(UserDTO user, ExpenseSummaryDTO overview) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("📊 Monthly Expense Report").setBold().setFontSize(16));
        doc.add(new Paragraph("User: " + user.name()));
        doc.add(new Paragraph("Email: " + user.email()));
        doc.add(new Paragraph("Period: " + overview.period().get("startDate") + " to " + overview.period().get("endDate")));
        doc.add(new Paragraph(" "));

        doc.add(new Paragraph("💰 Total Spent: ₹" + overview.totalSpent()));
        doc.add(new Paragraph("🏆 Top Category: " + overview.topCategory().get("category") +
                " (₹" + overview.topCategory().get("amount") + ")"));
        doc.add(new Paragraph(" "));

        doc.add(new Paragraph("📌 Category Breakdown:").setBold());

        Table table = new Table(2);
        table.addCell("Category");
        table.addCell("Amount");

        for (Map.Entry<String, Double> entry : overview.categoryBreakdown().entrySet()) {
            table.addCell(entry.getKey());
            table.addCell("₹" + entry.getValue());
        }

        doc.add(table);
        doc.close();

        return out.toByteArray();
    }
}
