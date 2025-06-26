package com.expense_tracker.report_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private final JavaMailSender mailSender;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReport(String to, byte[] pdfBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("📊 Monthly Expense Report");
        helper.setText("Hi,\n\nPlease find attached your monthly expense report.\n\nRegards,\nExpense Tracker");

        helper.addAttachment("monthly-report.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
}
