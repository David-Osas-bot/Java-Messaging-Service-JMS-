package com.message.Service;

import com.message.Entity.Messages;
import com.message.Repository.MessageRepository;
import com.message.RequestDTO.RequestDTO;
import com.message.ResponseDTO.ResponseDTO;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class MessageService {

    @Inject
    MessageRepository messageRepository;

    // CREATE USER
    public ResponseDTO createUser(RequestDTO request) {
        Messages entity = new Messages();
        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setPassword(request.getPassword());

        Messages saved = messageRepository.insert(entity);
        return toResponse(saved);
    }

    // GET PER USER
    public ResponseDTO getPerUser(Integer id) {

        Messages entity = messageRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        return toResponse(entity);
    }

    // GET ALL USERS
    public List<ResponseDTO> getAll() {

        return messageRepository.findAll()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

// UPDATE USER
    public ResponseDTO update(int id, RequestDTO request) {

        Messages entity = messageRepository.findById(id)
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

        Messages entity = messageRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return false;
        }

        messageRepository.deleteById(id);

        return true;
    }

    // ENTITY TO RESPONSE DTO
    private ResponseDTO toResponse(Messages entity) {

        ResponseDTO r = new ResponseDTO();
        r.setId(entity.getId());
        r.setFullName(entity.getFullName());
        r.setPhone(entity.getPhone());
        r.setEmail(entity.getEmail());

        return r;
    }
}
