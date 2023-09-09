package com.caido.appointments.Util;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.entity.Config;
import com.caido.appointments.repositories.ConfigRepository;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    ConfigRepository configRepository;
    protected String smtpHost;
    protected String smtpUser;
    protected String smtpPass;
    protected String smtpPort;
    protected String smtpLocalhost;
    protected String ssl;

    public MailService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
        Config c;
        c = this.configRepository.getMailServer();
        if(c==null) {
            throw new RootExceptionHandler("Configurati variabila MAIL_SERVER");
        } else {
            smtpHost = c.getValoare();
        }
        
        c = this.configRepository.getMailUser();
        if(c==null) {
            throw new RootExceptionHandler("Configurati variabila MAIL_USER");
        } else {
            smtpUser = c.getValoare();
        }
        
        
        c = this.configRepository.getMailPassword();
        if(c==null) {
            throw new RootExceptionHandler("Configurati variabila MAIL_PASSWORD");
        } else {
            smtpPass = c.getValoare();
        }
        
        
        c = this.configRepository.getMailSMTPPort();
        if(c==null) {
            throw new RootExceptionHandler("Configurati variabila MAIL_SMTP_PORT");
        } else {
            smtpPort = c.getValoare();
        }
        
        
        c = this.configRepository.getMailSMTPLocalhost();
        if(c==null) {
            throw new RootExceptionHandler("Configurati variabila MAIL_SMTP_LOCALHOST");
        } else {
            smtpLocalhost = c.getValoare();
        }
    }
    
    public void send(String from, String to, String subject, String text) throws Exception {
        try {
            System.out.println("smtpHost "+smtpHost+" smtpPort "+smtpPort+" smtpUser "+smtpUser+" smtpPass "+smtpPass);
            // Create a custom TrustManager that accepts all certificates
            TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };
            
            System.out.println("Start send mail");
            // Create an SSL context with the custom TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            Properties prop = new Properties();
            prop.put("mail.smtp.ssl.socketFactory", sslContext.getSocketFactory());
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", true);
            prop.put("mail.smtp.host", smtpHost);
            prop.put("mail.smtp.port", smtpPort);
            prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
            prop.put("mail.debug", true);
            //prop.put("mail.smtp.ssl.trust", smtpHost);
            prop.put("mail.smtp.ssl.trust", "*"); // Accept all certificates


            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUser, smtpPass);
                }
            });
            Message message = new MimeMessage(session);
            System.out.println("Send mail 1");
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            
            System.out.println("Send mail 2");
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=utf-8");
            
            System.out.println("Send mail 3");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            
            System.out.println("Send mail 4");
            message.setContent(multipart);
            System.out.println("Send mail 5");
            Transport.send(message);
            System.out.println("Send mail 6");
        } catch (AuthenticationFailedException e) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } catch (Exception e) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }
}
