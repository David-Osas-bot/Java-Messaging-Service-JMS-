package com.user.Service;

import com.user.Entity.User;
import com.user.RequestDTO.UserRequestDTO;
import com.user.ResponseDTO.UserResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import com.user.Repository.UserRepository;
import com.user.RequestDTO.LoginRequestDTO;

import com.user.RequestDTO.NotificationRequestDTO;
import com.user.producer.UserEventProducer;
import java.time.Instant;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordHashService passwordHashService;

    @Inject
    UserEventProducer userEventProducer;

    // CREATE USER
    public UserResponseDTO createUser(UserRequestDTO request) {
        User entity = new User();
        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setPasswordHash(
                passwordHashService.hashPassword(request.getPassword())
        );

        User saved = userRepository.insert(entity);

        // Publish registration event
        NotificationRequestDTO event = new NotificationRequestDTO();

        event.setUserId(saved.getId());
        event.setFullName(saved.getFullName());
        event.setEmail(saved.getEmail());
        event.setEventType("USER_REGISTERED");
        event.setOccurredAt(Instant.now());

        userEventProducer.sendEvent(event);

        return toResponse(saved);
    }

    // GET PER USER
    public UserResponseDTO getPerUser(Integer id) {

        User entity = userRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        return toResponse(entity);
    }

    // GET ALL USERS
    public List<UserResponseDTO> getAll() {

        return userRepository.findAll()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

// UPDATE USER
    public UserResponseDTO update(int id, UserRequestDTO request) {

        User entity = userRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());

        User updated = userRepository.save(entity);

        if (updated == null) {
            return null;
        }

        return toResponse(entity);
    }

    // DELETE USER
    public boolean delete(int id) {

        User entity = userRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return false;
        }

        userRepository.deleteById(id);

        return true;
    }

    // USER LOGIN
    public UserResponseDTO login(LoginRequestDTO request) {

        // 1. Find user using email
        User user = userRepository.findByEmail(
                request.getEmail().trim().toLowerCase()
        ).orElse(null);

        // 2. Check whether user exists
        if (user == null) {
            return null;
        }

        // 3. Verify submitted password against stored hash
        boolean passwordMatches
                = passwordHashService.verifyPassword(
                        request.getPassword(),
                        user.getPasswordHash()
                );

        if (!passwordMatches) {
            return null;
        }

        // Publish login event
        NotificationRequestDTO event = new NotificationRequestDTO();

        event.setUserId(user.getId());
        event.setFullName(user.getFullName());
        event.setEmail(user.getEmail());
        event.setEventType("USER_LOGGED_IN");
        event.setOccurredAt(Instant.now());

        userEventProducer.sendEvent(event);

        return toResponse(user);
    }

    // ENTITY TO RESPONSE DTO
    private UserResponseDTO toResponse(User entity) {

        UserResponseDTO r = new UserResponseDTO();
        r.setId(entity.getId());
        r.setFullName(entity.getFullName());
        r.setPhone(entity.getPhone());
        r.setEmail(entity.getEmail());

        return r;
    }
}
