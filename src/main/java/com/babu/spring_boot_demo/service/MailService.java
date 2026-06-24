package com.babu.spring_boot_demo.service;

import com.babu.spring_boot_demo.dto.MailResponse;
import com.babu.spring_boot_demo.dto.SendMailRequest;
import com.babu.spring_boot_demo.entity.*;
import com.babu.spring_boot_demo.repository.MailRepository;
import com.babu.spring_boot_demo.repository.UserMailFlagsRepository;
import com.babu.spring_boot_demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MailService
{
    private final MailRepository mailRepository;
    private final UserMailFlagsRepository flagsRepository;
    private final UserRepository userRepository;

    public MailService(
            MailRepository mailRepository,
            UserMailFlagsRepository flagsRepository,
            UserRepository userRepository)
    {
        this.mailRepository = mailRepository;
        this.flagsRepository = flagsRepository;
        this.userRepository = userRepository;
    }


    @Transactional // 1. டேட்டாபேஸ் பரிவர்த்தனை பாதுகாப்புக்கு
    public MailResponse sendMail(SendMailRequest request) // String-க்கு பதில் MailResponse ரிட்டர்ன் செய்கிறோம்
    {
        // MailEntity உருவாக்கம்


        if(userRepository.findByEmail(request.getSender()).isEmpty())
        {
            throw new RuntimeException("Sender not found");
        }

        if(userRepository.findByEmail(request.getReceiver()).isEmpty())
        {
            throw new RuntimeException("Receiver not found");
        }


        MailEntity mail = new MailEntity(
                request.getSender(),
                request.getReceiver(),
                request.getSubject(),
                request.getBody());

        UserMailFlagsEntity senderFlag = new UserMailFlagsEntity(mail, request.getSender(), FolderType.SENT);
        UserMailFlagsEntity receiverFlag = new UserMailFlagsEntity(mail, request.getReceiver(), FolderType.INBOX);

        try
        {
            // 2. சேமிப்பதற்கு முன்பே அனைத்து மதிப்புகளையும் சரியாக செட் செய்கிறோம்
            mail.setSentTime(LocalDateTime.now());
            mail.setStatus(MailStatus.SENT);

            senderFlag.setRead(true);
            senderFlag.setLabel(MailLabel.NORMAL);
            receiverFlag.setRead(false);

            mail.setSentTime(LocalDateTime.now());

            System.out.println(mail.getSentTime());

            //MailEntity savedMail = mailRepository.save(mail);



            // 3. டேட்டாபேஸில் சேமித்து, ரிட்டர்ன் ஆகும் அப்டேட்டட் ஆப்ஜெக்ட்டைப் பெறுகிறோம்
            MailEntity savedMail = mailRepository.save(mail);
            flagsRepository.save(senderFlag);
            flagsRepository.save(receiverFlag);

            System.out.println(savedMail.getSentTime());

            // 4. Response DTO-வை உருவாக்கி டேட்டாவை மேப் செய்கிறோம்
            MailResponse response = new MailResponse();
            response.setId(savedMail.getId()); // DB ஜெனரேட் செய்த ID
            response.setSender(savedMail.getSender());
            response.setReceiver(savedMail.getReceiver());
            response.setSubject(savedMail.getSubject());
            response.setBody(savedMail.getBody());
            response.setSentTime(savedMail.getSentTime()); // இப்போது இது null ஆக இருக்காது!
            response.setLabel(senderFlag.getLabel());
            response.setRead(senderFlag.isRead());

            return response;

        }
        catch (Exception e)
        {
            // எரர் வந்தால் Transaction ரோல்பேக் ஆகிவிடும்
            throw new RuntimeException("Mail sending failed: " + e.getMessage());
        }
    }



    public String moveToTrash(String email, Long mailId)
    {
        UserMailFlagsEntity flag =
                flagsRepository
                        .findByUserEmailAndMail_Id(
                                email,
                                mailId)
                        .orElseThrow();

        flag.setFolder(FolderType.TRASH);

        flagsRepository.save(flag);

        return "Moved to trash";
    }

    public String toggleStarMail(String email, Long mailId)
    {
        UserMailFlagsEntity flag =
                flagsRepository
                        .findByUserEmailAndMail_Id(
                                email,
                                mailId)
                        .orElseThrow();

        if (flag.getLabel()==MailLabel.NORMAL)
        {
            flag.setLabel(MailLabel.STARRED);
        }
        else
        {
            flag.setLabel(MailLabel.NORMAL);
        }

        flagsRepository.save(flag);

        return (flag.getLabel()==MailLabel.STARRED)?"Starred":"Star removed";
    }

    public String markAsRead(
            String email,
            Long mailId)
    {
        UserMailFlagsEntity flag =
                flagsRepository
                        .findByUserEmailAndMail_Id(
                                email,
                                mailId)
                        .orElseThrow();

        flag.setRead(true);

        flagsRepository.save(flag);

        return "Marked as read";
    }

    public String markAsUnread(
            String email,
            Long mailId)
    {
        UserMailFlagsEntity flag =
                flagsRepository
                        .findByUserEmailAndMail_Id(
                                email,
                                mailId)
                        .orElseThrow();

        flag.setRead(false);

        flagsRepository.save(flag);

        return "Marked as unread";
    }

    public String restoreMail(
            String email,
            Long mailId)
    {
        UserMailFlagsEntity flag =
                flagsRepository
                        .findByUserEmailAndMail_Id(
                                email,
                                mailId)
                        .orElseThrow();

        if(flag.getMail().getReceiver().equals(email))
        {
            flag.setFolder(FolderType.INBOX);
        }
        else
        {
            flag.setFolder(FolderType.SENT);
        }

        flagsRepository.save(flag);

        return "Restored";
    }

    public String deleteForever(
            String email,
            Long mailId)
    {
        flagsRepository.deleteByUserEmailAndMail_Id(
                email,
                mailId);

        return "Deleted permanently";
    }

    public List<MailResponse> getInbox(String email)
    {
        return flagsRepository
                .findByUserEmailAndFolder(
                        email,
                        FolderType.INBOX)
                .stream()
                .map(flag -> {

                    MailEntity mail = flag.getMail();

                    MailResponse response = new MailResponse();

                    response.setId(mail.getId());
                    response.setSender(mail.getSender());
                    response.setReceiver(mail.getReceiver());
                    response.setSubject(mail.getSubject());
                    response.setBody(mail.getBody());

                    response.setLabel(flag.getLabel());
                    response.setRead(flag.isRead());
                    response.setSentTime(flag.getMail().getSentTime());
                    return response;

                })
                .toList();
    }

    public List<MailResponse> getSentBox(String email)
    {
        return flagsRepository
                .findByUserEmailAndFolder(
                        email,
                        FolderType.SENT)
                .stream()
                .map(flag -> {

                    MailEntity mail = flag.getMail();

                    MailResponse response = new MailResponse();

                    response.setId(mail.getId());
                    response.setSender(mail.getSender());
                    response.setReceiver(mail.getReceiver());
                    response.setSubject(mail.getSubject());
                    response.setBody(mail.getBody());

                    response.setLabel(flag.getLabel());
                    response.setRead(flag.isRead());
                    response.setSentTime(flag.getMail().getSentTime());
                    return response;

                })
                .toList();
    }

    public List<MailResponse> getTrash(String email)
    {
        return flagsRepository
                .findByUserEmailAndFolder(
                        email,
                        FolderType.TRASH)
                .stream()
                .map(flag -> {

                    MailEntity mail = flag.getMail();

                    MailResponse response = new MailResponse();

                    response.setId(mail.getId());
                    response.setSender(mail.getSender());
                    response.setReceiver(mail.getReceiver());
                    response.setSubject(mail.getSubject());
                    response.setBody(mail.getBody());

                    response.setLabel(flag.getLabel());
                    response.setRead(flag.isRead());
                    response.setSentTime(mail.getSentTime());

                    return response;
                })
                .toList();
    }

    public List<MailResponse> getStarred(String email)
    {
        return flagsRepository
                .findByUserEmailAndLabel(
                        email,
                        MailLabel.STARRED)
                .stream()
                .map(flag -> {

                    MailEntity mail = flag.getMail();

                    MailResponse response = new MailResponse();

                    response.setId(mail.getId());
                    response.setSender(mail.getSender());
                    response.setReceiver(mail.getReceiver());
                    response.setSubject(mail.getSubject());
                    response.setBody(mail.getBody());

                    response.setLabel(flag.getLabel());
                    response.setRead(flag.isRead());
                    response.setSentTime(mail.getSentTime());

                    return response;
                })
                .toList();
    }


}
