package com.avinod.settingsmodule.controller;

import com.avinod.settingsmodule.entity.Notifications;
import com.avinod.settingsmodule.entity.User;
import com.avinod.settingsmodule.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostConstruct
    private void runAtStart(){
        notificationService.addTestDatas();
    }

    @GetMapping("/all")
    public List<Notifications> getAllNotifications() {
        return this.notificationService.getAllNotifications();
    }

    @GetMapping("/user/{id}")
    public List<Notifications> getNotificationsByUserId(@PathVariable(value = "id") int userId){
        return this.notificationService.getNotificationsByUserId(userId);
    }

    @PostMapping("/add/{id}")
    public String createNotification(@PathVariable(value = "id") int userId, @RequestBody Notifications notification){
        return this.notificationService.createNotification(userId, notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotification(@RequestBody Notifications notification, @PathVariable (value = "id") int userId){
        return this.notificationService.updateNotification(userId,notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable ("id") int userId){
        return this.notificationService.deleteNotification(userId);
    }
}
