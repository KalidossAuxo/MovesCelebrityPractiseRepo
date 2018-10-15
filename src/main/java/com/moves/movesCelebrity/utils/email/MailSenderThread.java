package com.moves.movesCelebrity.utils.email;
import com.moves.movesCelebrity.models.business.email.MailMessage;

import java.io.IOException;

public class MailSenderThread extends Thread {

    private boolean keepRunning = false;
    private boolean threadEnded = false;
    private LogEntryQueue mq;
    private int throttle = 3;

    public void startThread() throws IOException {
        System.out.println("Starting Mail thread");
        mq = LogEntryQueue.getInstance();
        start();
    }

    public void stopThread() {
        if (keepRunning == false) {
            return;
        }
        threadEnded = false;
        keepRunning = false;
        while (!threadEnded) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    public void run() {
        keepRunning = true;
        try {
            while (keepRunning) {
                sendPendingMails();
                Thread.sleep(30000);
            }
        } catch (Exception ex) {
            threadEnded = true;
            ex.printStackTrace();
        }
        keepRunning = false;
        threadEnded = true;
    }

    public synchronized void sendPendingMails() {
        MailMessage mailMessage;
        GMailSender mailSender = new GMailSender();
        if (mq == null)
            mq = LogEntryQueue.getInstance();
        while ((mailMessage = mq.dequeue()) != null) {
            try {
                mailMessage.incrementAttempt();
                mailSender.sendEmail(mailMessage);
            } catch (Exception e) {
                if (mailMessage.getAttemptCount() < throttle)
                    mq.enqueue(mailMessage);
            }
        }
    }

}
