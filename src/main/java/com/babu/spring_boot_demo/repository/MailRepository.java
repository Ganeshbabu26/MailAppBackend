package com.babu.spring_boot_demo.repository;

import com.babu.spring_boot_demo.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<MailEntity, Long>
{
    List<MailEntity> findByReceiver(String receiver);
    List<MailEntity> findBySender(String sender);
}