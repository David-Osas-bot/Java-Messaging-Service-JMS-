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

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordHashService passwordHashService;

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

        boolean updated = userRepository.update(entity);

        if (!updated) {
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

        // 4. Return safe user information
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
