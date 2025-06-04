package com.codigoQR.sistemQrCode.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class EmailFuncionario {
    private JavaMailSender mailSender;

    public EmailFuncionario(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarQrCodePorEmail(String destinatario, byte[] imagemQrCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(destinatario);
            helper.setSubject("Seu QR Code de acesso");
            helper.setText("Segue em anexo seu QR Code para acesso.");


            helper.addAttachment("qrcode.png", new ByteArrayResource(imagemQrCode));

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail com QR Code", e);
        }
    }
}
