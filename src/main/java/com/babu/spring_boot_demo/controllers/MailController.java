package com.babu.spring_boot_demo.controllers;
import com.babu.spring_boot_demo.dto.MailResponse;
import com.babu.spring_boot_demo.dto.SendMailRequest;
import com.babu.spring_boot_demo.service.MailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
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
    public MailResponse sendMail(
            @RequestBody SendMailRequest request,
            Authentication auth)
    {
        return mailService.sendMail(
                auth.getName(),
                request);
    }

    @GetMapping("/inbox")
    public List<MailResponse> getInbox(
            Authentication auth)
    {
        return mailService.getInbox(
                auth.getName());
    }

    @GetMapping("/sent")
    public List<MailResponse> getSent(
            Authentication auth)
    {
        return mailService.getSentBox(
                auth.getName());
    }

    @GetMapping("/trash")
    public List<MailResponse> getTrash(
            Authentication auth)
    {
        return mailService.getTrash(
                auth.getName());
    }

    @GetMapping("/starred")
    public List<MailResponse> getStarred(
            Authentication auth)
    {
        return mailService.getStarred(
                auth.getName());
    }

    @PutMapping("/trash/{mailId}")
    public String moveToTrash(
            @PathVariable Long mailId,
            Authentication auth)
    {
        return mailService.moveToTrash(
                auth.getName(),
                mailId);
    }

    @PutMapping("/star/{mailId}")
    public String toggleStar(
            @PathVariable Long mailId,
            Authentication auth)
    {
        return mailService.toggleStarMail(
                auth.getName(),
                mailId);
    }

    @GetMapping("/{mailId}")
    public MailResponse getMail(
            @PathVariable Long mailId,
            Authentication auth)
    {
        return mailService.getMail(
                auth.getName(),
                mailId);
    }

    @GetMapping("/draft")
    public List<MailResponse> getDraft(
            Authentication auth)
    {
        return mailService.getDraft(
                auth.getName());
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