package com.avinod.settingsmodule.service;

import com.avinod.settingsmodule.entity.Notifications;
import com.avinod.settingsmodule.entity.User;
import com.avinod.settingsmodule.exception.ResourceNotFoundException;
import com.avinod.settingsmodule.repository.NotificationRepository;
import com.avinod.settingsmodule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public void addTestDatas(){
        Notifications notifications1=new Notifications(1,"Message1",false,new java.util.Date());
        Notifications notifications2=new Notifications(2,"Message2",true,new java.util.Date());
        List<Notifications> notify=new ArrayList<>();
        notify.add(notifications1);
        notify.add(notifications2);
        User user=new User(1,"Abhi","Vinod","abhi@gmail.com",notify);
        userRepository.save(user);
    }

    public List<Notifications> getAllNotifications(){
        return this.notificationRepository.findAll();
    }

    public List<Notifications> getNotificationsByUserId(int userId){
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));
        return user.getNotifications();
    }

    public String createNotification(int userId,Notifications notification){
        User user=userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));
        user.getNotifications().add(notification);
        this.userRepository.save(user);
        return "Notification added to user successfully";
    }

    public ResponseEntity<?> updateNotification(int userId,Notifications notifications){
        User user=userRepository
                .findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));
        List<Notifications> updateNotification=user.getNotifications();
        Iterator<Notifications> iterator=updateNotification.iterator();
        while(iterator.hasNext()){
            Notifications notify=iterator.next();
            if(notify.getNotificationId()==notifications.getNotificationId()){
                notify.setRead(true);
            }
        }
        user.setNotifications(updateNotification);
        this.userRepository.save(user);
        return new ResponseEntity("Notification updated Successfully",HttpStatus.OK);
    }

    public ResponseEntity<?> deleteNotification(int userId){
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));
        user.getNotifications().clear();
        this.userRepository.save(user);
        return new ResponseEntity<>("Notifications of userId "+userId+" are successfully deleted", HttpStatus.OK);
    }

}
