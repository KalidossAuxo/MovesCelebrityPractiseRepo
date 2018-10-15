package com.moves.movesCelebrity.utils.email;


import com.moves.movesCelebrity.models.business.email.MailMessage;

import java.util.LinkedList;
import java.util.Queue;

public class LogEntryQueue {

    private static LogEntryQueue mailQObj;
    private Queue<MailMessage> mailQueue;

    private LogEntryQueue() {
        mailQueue = new LinkedList<MailMessage>();
    }

    public static synchronized LogEntryQueue getInstance() {
        if (mailQObj == null) {
            mailQObj = new LogEntryQueue();
        }
        return mailQObj;
    }

    public void enqueue(MailMessage mailMessage) {
        mailQueue.add(mailMessage);
    }

    public MailMessage dequeue() {
        return mailQueue.poll();
    }

}
