package service;


import values.MessageValues;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService implements IJavaMail {
    @Override
    public boolean send(String to, String subject, String messageContent) {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailProperty.HOST_NAME); // smtp.gmail.com
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //
        props.put("mail.smtp.port", EmailProperty.TSL_PORT);
        props.put("mail.smtp.ssl.trust", EmailProperty.HOST_NAME);

        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("javax.net.ssl.SSLContext", "TLSv1.2");


        // get Session
        Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(EmailProperty.APP_EMAIL, EmailProperty.APP_PASSWORD);
            }

        });


        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            ((MimeMessage) message).setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageContent,"UTF-8");

            // send message
            Transport.send(message);
            return true;
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendHTML(String to, String subject, String messageContent) {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailProperty.HOST_NAME); // smtp.gmail.com
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // KÃ­ch hoáº¡t STARTTLS
        props.put("mail.smtp.port", EmailProperty.TSL_PORT); // Cá»•ng 587
        props.put("mail.smtp.ssl.trust", EmailProperty.HOST_NAME); // Chá»©ng thá»±c SSL

        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("javax.net.ssl.SSLContext", "TLSv1.2");


        // get Session
        Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(EmailProperty.APP_EMAIL, EmailProperty.APP_PASSWORD);
            }

        });


        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            ((MimeMessage) message).setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
//            message.setText(messageContent);
            message.setContent(messageContent, "text/html; charset=UTF-8");


            // send message
            Transport.send(message);
            return true;
        } catch (javax.mail.MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
//        String to = "EQR4@st.hcmuaf.edu.vn";
//        String subject = "TEST";
//        String message = "HI  NHAT";
//        IJavaMail emailService = new EmailService();
//        emailService.send(to, subject, message);

        EmailService emailService = new EmailService();
        String mailContent = MessageValues.getOTP_VERIFY_ACCOUNT_MESSAGE("!@3");
        boolean re =emailService.sendHTML("qtqn",MessageValues.WEB_NAME, mailContent);
        System.out.println(re);
    }
}


