package com.PortFolio.MyPort.Controller;

import com.PortFolio.MyPort.Entity.PortFolioEn;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class PortFolio {

    private final JavaMailSender javaMailSender;

    public PortFolio(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/sendemail")
    public ResponseEntity<String> sendMail(@RequestBody PortFolioEn portFolioEn){
        String subject = portFolioEn.getSubject();
        String object = portFolioEn.getObject();

        try {
            portFolioEn.setEmail("aadhil8336@gmail.com");
            sendEmail(portFolioEn.getEmail(), subject, object);
            return ResponseEntity.ok().body("Email send successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email send is failed");
        }
    }

    public void sendEmail(String email, String subject, String object) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(object);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }
}
