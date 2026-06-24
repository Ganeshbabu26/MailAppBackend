package com.babu.spring_boot_demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_mail_flags")
public class UserMailFlagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mail_id", nullable = false)
    private MailEntity mail;

    private String userEmail;

    @Enumerated(EnumType.STRING)
    private MailLabel label;

    private boolean isRead = false;

    @Enumerated(EnumType.STRING)
    private FolderType folder;

    public UserMailFlagsEntity() {
    }

    public UserMailFlagsEntity(MailEntity mail, String userEmail, FolderType folder) {
        this.mail = mail;
        this.userEmail = userEmail;
        this.folder = folder;
        this.label = MailLabel.NORMAL;
    }

    public Long getId() {
        return id;
    }

    public MailEntity getMail() {
        return mail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public MailLabel getLabel() {
        return this.label;
    }

    public void setLabel(MailLabel label) {
        this.label = label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(MailEntity mail) {
        this.mail = mail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public FolderType getFolder() {
        return folder;
    }


    public void setFolder(FolderType folder) {
        this.folder = folder;
    }

    public void setFolder(MailLabel label) {
        this.label = label;
    }
}