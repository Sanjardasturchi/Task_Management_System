package com.example.service;

import com.example.config.details.CustomUserDetails;
import com.example.dto.TaskDTO;
import com.example.dto.extra.CreateTask;
import com.example.dto.extra.UpdateTask;
import com.example.dto.extra.UpdateTaskPriority;
import com.example.dto.extra.UpdateTaskStatus;
import com.example.entity.PerformerEntity;
import com.example.entity.TaskEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.PerformerRepository;
import com.example.repository.ProfileRepository;
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
public class TaskService {
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PerformerRepository performerRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public TaskDTO create(CreateTask taskDTO, AppLanguage language) {
        String profileId = getUserDetails().getId();
        TaskEntity taskEntity =new TaskEntity();
        taskEntity.setOwnerId(profileId);
        taskEntity.setTitle(taskDTO.getTitle());
        if (taskDTO.getTaskPriority() == null || taskDTO.getTaskStatus() == null) {
            throw new AppBadException(resourceBundleService.getMessage("data.is.incomplete", language));
        }
        taskEntity.setTaskPriority(taskDTO.getTaskPriority());
        taskEntity.setTaskStatus(taskDTO.getTaskStatus());
        taskEntity.setCreatedDate(LocalDateTime.now());
        taskRepository.save(taskEntity);

        for (String performer : taskDTO.getPerformers()) {
            if (profileRepository.findById(performer).isEmpty()) {
                throw new AppBadException(resourceBundleService.getMessage("profile.not.found", language));
            }
            PerformerEntity entity=new PerformerEntity();
            entity.setPerformerId(performer);
            entity.setTaskId(taskEntity.getId());
            performerRepository.save(entity);
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(taskEntity.getId());
        dto.setOwnerId(profileId);
        dto.setTitle(taskDTO.getTitle());
        dto.setTaskPriority(taskDTO.getTaskPriority());
        dto.setTaskStatus(taskDTO.getTaskStatus());
        dto.setCreatedDate(taskEntity.getCreatedDate());
        dto.setPerformerIdList(taskDTO.getPerformers());
        return dto;
    }

    /**
     * This method is used to get UserDetails
     */
    private static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public PageImpl<TaskDTO> getAll(Integer size, Integer page, AppLanguage language) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<TaskEntity> commentEntities = taskRepository.findAll(pageable);
        List<TaskEntity> entityList = commentEntities.getContent();

        long totalElements = commentEntities.getTotalElements();
        List<TaskDTO> dtoList = new LinkedList<>();

        for (TaskEntity comment : entityList) {
            dtoList.add(toDTO(comment));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public TaskDTO update(UpdateTask taskDTO, AppLanguage language) {
        Optional<TaskEntity> optional = taskRepository.findById(taskDTO.getTaskId());
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("task.not.found", language));  // Task not found
        }
        String profileId = getUserDetails().getId();
        TaskEntity entity = optional.get();
        if (!profileId.equals(entity.getOwnerId())) {
            throw new AppBadException(resourceBundleService.getMessage("you.can.not.update.info", language)); // You can`t update
        }
        taskRepository.updateTitleById(taskDTO.getTitle(), taskDTO.getTaskId());

        TaskDTO dto =toDTO(entity);
        dto.setTitle(taskDTO.getTitle());
        return dto;
    }

    public TaskDTO updateStatus(UpdateTaskStatus taskDTO, AppLanguage language) {
        Optional<TaskEntity> optional = taskRepository.findById(taskDTO.getTaskId());
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("task.not.found", language));  // Task not found
        }
        String profileId = getUserDetails().getId();
        TaskEntity entity = optional.get();
        List<String> performerIdList = performerRepository.findAllPerformerByTaskId(entity.getId());
        performerIdList.add(entity.getOwnerId());
        if (!performerIdList.contains(profileId)) {
            throw new AppBadException(resourceBundleService.getMessage("you.can.not.update.info", language)); // You can`t update
        }
        taskRepository.updateStatusById(taskDTO.getTaskStatus(), taskDTO.getTaskId());

        TaskDTO dto =toDTO(entity);
        dto.setTaskStatus(taskDTO.getTaskStatus());
        return dto;
    }

    public TaskDTO updatePriority(UpdateTaskPriority taskDTO, AppLanguage language) {
        Optional<TaskEntity> optional = taskRepository.findById(taskDTO.getTaskId());
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("task.not.found", language));  // Task not found
        }
        String profileId = getUserDetails().getId();
        TaskEntity entity = optional.get();
        if (!profileId.equals(entity.getOwnerId())) {
            throw new AppBadException(resourceBundleService.getMessage("you.can.not.update.info", language)); // You can`t update
        }
        taskRepository.updatePriorityById(taskDTO.getTaskPriority(), taskDTO.getTaskId());

        TaskDTO dto =toDTO(entity);
        dto.setTaskPriority(taskDTO.getTaskPriority());
        return dto;
    }

    public TaskDTO toDTO(TaskEntity entity){
        TaskDTO dto=new TaskDTO();
        dto.setId(entity.getId());
        dto.setOwnerId(entity.getOwnerId());
        dto.setTitle(entity.getTitle());
        dto.setTaskStatus(entity.getTaskStatus());
        dto.setTaskPriority(entity.getTaskPriority());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPerformerIdList(performerRepository.findAllPerformerByTaskId(entity.getId()));
        return dto;
    }

    public PageImpl<TaskDTO> getByOwnerId(String id, Integer size, Integer page, AppLanguage language) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<TaskEntity> taskEntities = taskRepository.findByOwnerId(id, pageable);
        List<TaskEntity> entityList = taskEntities.getContent();

        long totalElements = taskEntities.getTotalElements();
        List<TaskDTO> dtoList = new LinkedList<>();

        for (TaskEntity comment : entityList) {
            dtoList.add(toDTO(comment));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public Boolean delete(String id, AppLanguage language) {
        Optional<TaskEntity> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("task.not.found", language));  // Task not found
        }
        String profileId = getUserDetails().getId();
        TaskEntity entity = optional.get();
        if (!profileId.equals(entity.getOwnerId())) {
            throw new AppBadException(resourceBundleService.getMessage("you.can.not.update.info", language)); // You can`t update
        }
        taskRepository.deleteById(id);

        return true;
    }
}
