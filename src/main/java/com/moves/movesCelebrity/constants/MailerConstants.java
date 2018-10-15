package com.moves.movesCelebrity.constants;

public class MailerConstants {

        //Session property  keys
        public static String MAIL_SMTP_HOST = "mail.smtp.host";
        public static String MAIL_SMTP_FACTORY_PORT = "mail.smtp.socketFactory.port";
        public static String MAIL_SMTP_FACTORY_CLASS = "mail.smtp.socketFactory.class";
        public static String MAIL_SMTP_AUTH = "mail.smtp.auth";
        public static String MAIL_SMTP_PORT = "mail.smtp.port";

        //Email constants
        public static String NO_REPLY_SITE = "www.mc.com";
        public static String NO_REPLY_TS = "noreply@gmail.com";
        public static String EMAIl_RESPOSE_TEXT = "Email send response :";
        public static String SEND_EMAIL_SUCCESFULLY = "Email send successfully";
        public static String SEND_EMAIL_FAILED = "Failure! please try again";

        //Sign up
        public static String SIGNUP_BODY = "Dear %1$s, %2$s<br /><br />" +
                "Thanks for registering with MOVES. %3$s is your verification code. <br />" +
                "Kindly enter this code to verify your account and then you can continue to use your moves application.<br /><br/>"+
                "Thanks.<br />" +
                "MOVES";
        public static String SIGNUP_SUBJECT = "MOVES :: Registration Confirmation";



}
