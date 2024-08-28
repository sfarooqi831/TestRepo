package com.alivia.bussiness.service;



import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alivia.bussiness.dto.EmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
  @Autowired
  private JavaMailSender javaMailSender;
  
  @Autowired
  ReportService reportService;
  
  @Value("${report.send.email.tolist}")
  private String toEmailList;
  
  @Autowired
  SimpleMailMessage simpleMailMessage;
  
  @Value("${spring.mail.username}")
  private String sender;
  
  public String sendSimpleEmail(@RequestBody EmailRequest emailRequest) {
    this.simpleMailMessage.setFrom(this.sender);
    this.simpleMailMessage.setTo(emailRequest.getReciver());
    this.simpleMailMessage.setSubject(emailRequest.getSubject());
    this.simpleMailMessage.setText(emailRequest.getEmailBody());
    this.javaMailSender.send(this.simpleMailMessage);
    return String.format("Email sent to %s", new Object[] { emailRequest.getReciver() + " successfully" });
  }
  
  public String sendAttchmentEmail(@RequestBody EmailRequest emailRequest) throws MessagingException, MalformedURLException {
    MimeMessage mime = this.javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mime, (null != emailRequest.getAttachement() && (emailRequest.getAttachement()).length > 0));
    helper.setFrom(this.sender);
    helper.setTo(new String[] { "ameermuavia229@gmail.com", "ameermuaviaa116@gmail.com", "sfarooqi831@gmail.com", this.sender });
    helper.setSubject(emailRequest.getSubject());
    helper.setText(emailRequest.getEmailBody(), true);
    if (null != emailRequest.getAttachement() && (emailRequest.getAttachement()).length > 0)
      for (String attachmentPath : emailRequest.getAttachement()) {
        FileSystemResource fileSystemResource = null;
        if (attachmentPath.startsWith("http://") || attachmentPath.startsWith("https://")) {
          UrlResource urlResource = new UrlResource(attachmentPath);
        } else {
          fileSystemResource = new FileSystemResource(attachmentPath);
        } 
        helper.addAttachment(fileSystemResource.getFilename(), (InputStreamSource)fileSystemResource);
      }  
    this.javaMailSender.send(mime);
    return String.format("Email sent to %s", new Object[] { emailRequest.getReciver() + " successfully" });
  }
  
  public String sendEmail(EmailRequest request) throws MessagingException {
    MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
    helper.setText(request.getEmailBody(), true);
    helper.setSubject(request.getSubject());
    helper.setTo(request.getReciver());
    helper.setSentDate(new Date());
    helper.setBcc(this.sender);
    this.javaMailSender.send(mimeMessage);
    return String.format("Email sent to %s", new Object[] { request.getReciver() + " successfully" });
  }
  
  public String sendOrderReport() throws MessagingException, IOException {
    MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setTo(this.toEmailList.split(","));
    String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    helper.setSubject("[" + date + "] Order report");
    helper.setText("Hi,\n\nPlease find today's order report attached.\n\nThanks in anticipation");
    byte[] report = this.reportService.generateReport();
    Resource content = new ByteArrayResource(report);
    helper.addAttachment(date + "_order_report.xlsx", (InputStreamSource)content);
    this.javaMailSender.send(mimeMessage);
    return "Report sent to order manager";
  }
}
