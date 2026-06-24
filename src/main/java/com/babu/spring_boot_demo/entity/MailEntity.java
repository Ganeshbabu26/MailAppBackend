package com.babu.spring_boot_demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="mails")
public class MailEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String receiver;
    private String subject;

    @Column(length = 5000)
    private String body;


    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private MailStatus status;

    public MailEntity(String sender, String receiver, String subject, String body)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public MailEntity()
    {

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public LocalDateTime getSentTime() { return sentTime; }
    public void setSentTime(LocalDateTime sentTime) { this.sentTime = sentTime; }

    public MailStatus getStatus() { return status; }
    public void setStatus(MailStatus status) { this.status = status; }
}
