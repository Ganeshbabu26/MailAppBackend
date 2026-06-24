package com.babu.spring_boot_demo.controllers;
import com.babu.spring_boot_demo.dto.MailResponse;
import com.babu.spring_boot_demo.dto.SendMailRequest;
import com.babu.spring_boot_demo.entity.MailEntity;
import com.babu.spring_boot_demo.entity.UserMailFlagsEntity;
import com.babu.spring_boot_demo.service.MailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailController
{
    private final MailService mailService;

    public MailController(MailService mailService)
    {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public MailResponse sendMail(@RequestBody SendMailRequest request)
    {
        return mailService.sendMail(request);
    }

    @GetMapping("/inbox/{email}")
    public List<MailResponse> getInbox(@PathVariable String email)
    {
        return mailService.getInbox(email);
    }

    @GetMapping("/sent/{email}")
    public List<MailResponse> getSent(@PathVariable String email)
    {
        return mailService.getSentBox(email);
    }

    @GetMapping("/trash/{email}")
    public List<MailResponse> getTrash(
            @PathVariable String email)
    {
        return mailService.getTrash(email);
    }

    @GetMapping("/starred/{email}")
    public List<MailResponse> getStarred(
            @PathVariable String email)
    {
        return mailService.getStarred(email);
    }

    @PutMapping("/trash/{email}/{mailId}")
    public String moveToTrash(
            @PathVariable String email,
            @PathVariable Long mailId)
    {
        return mailService.moveToTrash(email, mailId);
    }

    @PutMapping("/star/{email}/{mailId}")
    public String toggleStar(
            @PathVariable String email,
            @PathVariable Long mailId)
    {
        return mailService.toggleStarMail(email, mailId);
    }

}
/*
Draft Mail
Scheduled Mail
Safe Folder
Search Mail
Pagination
JWT Authentication
File Attachments
Conversation Threads (like Gmail)
Labels/Categories
Notification Service

 */