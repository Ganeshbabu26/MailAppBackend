package com.babu.spring_boot_demo.repository;

import com.babu.spring_boot_demo.entity.FolderType;
import com.babu.spring_boot_demo.entity.MailLabel;
import com.babu.spring_boot_demo.entity.UserMailFlagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMailFlagsRepository extends JpaRepository<UserMailFlagsEntity, Long>
{
    Optional<UserMailFlagsEntity> findByUserEmailAndMail_Id  (String email, Long mailId);

    List<UserMailFlagsEntity>
    findByUserEmailAndFolder(String email, FolderType folderType);

    List<UserMailFlagsEntity>
    findByUserEmailAndLabel(String email, MailLabel label);

    void deleteByUserEmailAndMail_Id(
            String email,
            Long mailId);

}
