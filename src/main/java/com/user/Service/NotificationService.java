package com.user.Service;

import com.user.Entity.Notification;
import com.user.Repository.NotificationRepository;
import com.user.RequestDTO.NotificationRequestDTO;
import com.user.ResponseDTO.NotificationResponseDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

    // SAVE NOTIFICATION (your original method)
    public NotificationResponseDTO saveNotification(
            NotificationRequestDTO request) {

        Notification notification = new Notification();

        notification.setUserId(request.getUserId());
        notification.setEventType(request.getEventType());
        notification.setFullName(request.getFullName());
        notification.setEmail(request.getEmail());
        notification.setOccurredAt(request.getOccurredAt());
        notification.setReceivedAt(Instant.now());

        Notification saved = notificationRepository.insert(notification);

        return toResponse(saved);
    }

    // GET ONE NOTIFICATION BY ID
    public NotificationResponseDTO getById(Integer id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification not found"));

        return toResponse(notification);
    }

    // GET ALL NOTIFICATIONS FOR A USER
    public List<NotificationResponseDTO> getByUserId(Integer userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);

        List<NotificationResponseDTO> result = new ArrayList<>();
        for (Notification n : notifications) {
            result.add(toResponse(n));
        }
        return result;
    }

    // DELETE A NOTIFICATION
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }

    // ENTITY TO RESPONSE DTO (your original method)
    private NotificationResponseDTO toResponse(Notification entity) {

        NotificationResponseDTO response = new NotificationResponseDTO();

        response.setId(entity.getId());
        response.setUserId(entity.getUserId());
        response.setEventType(entity.getEventType());
        response.setFullName(entity.getFullName());
        response.setEmail(entity.getEmail());
        response.setOccurredAt(entity.getOccurredAt());

        return response;
    }
}
