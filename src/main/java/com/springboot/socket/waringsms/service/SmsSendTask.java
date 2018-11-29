package com.springboot.socket.waringsms.service;

/**
 * Created by dell on 2017/8/4.
 */
public class SmsSendTask {
    public String toAddress = null;
    public String subject = null;
    public String content = null;

    public SmsSendTask(String toAddress, String subject, String content) {
        this.toAddress = toAddress;
        this.content = content;
        this.subject = subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
