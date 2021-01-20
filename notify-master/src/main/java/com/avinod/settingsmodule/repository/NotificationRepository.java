package com.avinod.settingsmodule.repository;

import com.avinod.settingsmodule.entity.Notifications;
import com.avinod.settingsmodule.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Integer> {
}
