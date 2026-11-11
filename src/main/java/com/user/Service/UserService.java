package com.user.Service;

import com.user.Entity.User;
import com.user.RequestDTO.UserRequestDTO;
import com.user.ResponseDTO.UserResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;
import com.user.Repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository messageRepository;

    // CREATE USER
    public UserResponseDTO createUser(UserRequestDTO request) {
        User entity = new User();
        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setPassword(request.getPassword());

        User saved = messageRepository.insert(entity);
        return toResponse(saved);
    }

    // GET PER USER
    public UserResponseDTO getPerUser(Integer id) {

        User entity = messageRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        return toResponse(entity);
    }

    // GET ALL USERS
    public List<UserResponseDTO> getAll() {

        return messageRepository.findAll()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

// UPDATE USER
    public UserResponseDTO update(int id, UserRequestDTO request) {

        User entity = messageRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setPassword(request.getPassword());

        boolean updated = messageRepository.update(entity);

        if (!updated) {
            return null;
        }

        return toResponse(entity);
    }

    // DELETE USER
    public boolean delete(int id) {

        User entity = messageRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return false;
        }

        messageRepository.deleteById(id);

        return true;
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
