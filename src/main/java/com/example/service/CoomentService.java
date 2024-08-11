package com.example.service;

import com.example.config.details.CustomUserDetails;
import com.example.dto.CommentDTO;
import com.example.dto.extra.CreateComment;
import com.example.entity.CommentEntity;
import com.example.entity.TaskEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.CommentRepository;
import com.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CoomentService {
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;


    public CommentDTO create(CreateComment commentDTO, AppLanguage language) {
        String profileId = getUserDetails().getId();
        Optional<TaskEntity> optional = taskRepository.findById(commentDTO.getTaskId());
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("task.not.found", language));  // Task not found
        }
        CommentEntity entity =new CommentEntity();
        entity.setOwnerId(profileId);
        entity.setTaskId(commentDTO.getTaskId());
        entity.setMessage(commentDTO.getMessage());
        entity.setCreatedDate(LocalDateTime.now());
        commentRepository.save(entity);
        return toDTO(entity);
    }

    /**
     * This method is used to get UserDetails
     */
    private static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public CommentDTO toDTO(CommentEntity entity){
        CommentDTO dto=new CommentDTO();
        dto.setId(entity.getId());
        dto.setOwnerId(entity.getOwnerId());
        dto.setTaskId(entity.getTaskId());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<CommentDTO> getAll(Integer size, Integer page, AppLanguage language) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> commentEntities = commentRepository.findAll(pageable);
        List<CommentEntity> entityList = commentEntities.getContent();

        long totalElements = commentEntities.getTotalElements();
        List<CommentDTO> dtoList = new LinkedList<>();

        for (CommentEntity comment : entityList) {
            dtoList.add(toDTO(comment));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<CommentDTO> getByTaskId(String taskId, Integer size, Integer page, AppLanguage language) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> commentEntities = commentRepository.findByTaskId(taskId, pageable);
        List<CommentEntity> entityList = commentEntities.getContent();

        long totalElements = commentEntities.getTotalElements();
        List<CommentDTO> dtoList = new LinkedList<>();

        for (CommentEntity comment : entityList) {
            dtoList.add(toDTO(comment));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }
}
