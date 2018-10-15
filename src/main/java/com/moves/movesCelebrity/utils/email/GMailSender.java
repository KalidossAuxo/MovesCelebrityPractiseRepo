package com.moves.movesCelebrity.utils.email;

import com.moves.movesCelebrity.configuration.MovesAPISystemConfiguration;
import com.moves.movesCelebrity.constants.MailerConstants;
import com.moves.movesCelebrity.models.business.email.MailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class GMailSender extends javax.mail.Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(GMailSender.class);


    private static  Session session;

    public GMailSender() {
        createEmailSession();
    }

    private void createEmailSession() {
        if (session == null) {
            logger.info("Adding email properties");
            Properties properties = new Properties();
            properties.put(MailerConstants.MAIL_SMTP_HOST, MovesAPISystemConfiguration.getMailServiceHost());
            properties.put(MailerConstants.MAIL_SMTP_FACTORY_PORT, MovesAPISystemConfiguration.getMailServicePort());
            properties.put(MailerConstants.MAIL_SMTP_FACTORY_CLASS, MovesAPISystemConfiguration.getMailServiceFactoryClass());
            properties.put(MailerConstants.MAIL_SMTP_AUTH, MovesAPISystemConfiguration.getMailServiceAuth());
            properties.put(MailerConstants.MAIL_SMTP_PORT, MovesAPISystemConfiguration.getMailServicePort());

            logger.info("Authenticating email session");
            javax.mail.Authenticator authenticator = new javax.mail.Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MovesAPISystemConfiguration.getMailerAddress(), MovesAPISystemConfiguration.getMailerPassword());
                }
            };
            session = Session.getDefaultInstance(properties, authenticator);
        }
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(MovesAPISystemConfiguration.getMailerAddress(), MovesAPISystemConfiguration.getMailerPassword());
    }

    public synchronized boolean sendEmail(MailMessage mailMessage) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailerConstants.NO_REPLY_SITE, MailerConstants.NO_REPLY_TS));
            message.setReplyTo(InternetAddress.parse(MovesAPISystemConfiguration.getMailerAddress(), false));
            message.setSubject(mailMessage.getSubject());
            message.setContent(mailMessage.getBody(), "text/html");
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(mailMessage.getRecipientEmail()));
            Transport.send(message);
            logger.info(MailerConstants.EMAIl_RESPOSE_TEXT,MailerConstants.SEND_EMAIL_SUCCESFULLY);
            return true;
        } catch (Exception e) {
            logger.info(MailerConstants.EMAIl_RESPOSE_TEXT,MailerConstants.SEND_EMAIL_FAILED);
            return false;
        }
    }


}
