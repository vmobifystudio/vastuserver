package com.vishwakarma.vastu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishwakarma.vastu.model.MessageLog;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {

}

