package com.flow.railwayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flow.railwayservice.domain.RAudioNotification;

public interface AudioNotificationRepository extends JpaRepository<RAudioNotification, Long> {

}
