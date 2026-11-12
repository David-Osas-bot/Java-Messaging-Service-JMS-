package com.user.Repository;

import com.user.Entity.Notification;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface NotificationRepository
        extends CrudRepository<Notification, Integer> {

    // Jakarta Data builds this query from the method name
    List<Notification> findByUserId(Integer userId);
}
