package com.example.service;

import com.example.enums.ProfileStatus;
import com.example.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    /**
     * this method is used to change the user's status 👇🏻
     */
    public void changeStatus(String id, ProfileStatus status) {
        profileRepository.updateStatus(id,status);
    }

}
