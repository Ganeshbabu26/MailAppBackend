package com.babu.spring_boot_demo.dto;

import com.babu.spring_boot_demo.entity.MailLabel;

import java.time.LocalDateTime;

public class MailResponse
{
    private Long id;
    private String sender;
    private String receiver;
    private String subject;
    private String body;
    private MailLabel label;
    private boolean read;
    private LocalDateTime sentTime;

    public MailResponse() {}

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public MailLabel getLabel()
    {
        return label;
    }

    public void setLabel(MailLabel label)
    {
        this.label = label;
    }

    public boolean isRead()
    {
        return read;
    }

    public void setRead(boolean read)
    {
        this.read = read;
    }

    public LocalDateTime getSentTime()
    {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) { this.sentTime = sentTime; }
}