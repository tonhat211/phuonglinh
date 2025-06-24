package service;

import DAO.MailConfigDAO;
import model.MailConfig;

public class EmailProperty {
    public static final String HOST_NAME = "smtp.gmail.com";

    public static final int SSL_PORT = 465; // Port for SSL

    public static final int TSL_PORT = 587; // Port for TLS/STARTTLS

    private static MailConfig mailConfig = MailConfigDAO.getInstance().selectById(1);

    public static String APP_EMAIL = mailConfig.getEmail(); // your email

    public static String APP_PASSWORD = Vigenere.decrypt(APP_EMAIL, mailConfig.getPwd()); // your password



}
