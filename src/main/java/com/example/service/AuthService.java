package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.extra.Auth;
import com.example.dto.extra.Registration;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailSentHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.utils.JWTUtil;
import com.example.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private EmailSentHistoryRepository emailSendHistoryRepository;

    /**
     * this method is used for profile login. In this, the necessary information is requested from the profile, and the profile database with this information is checked, and its status is checked.
     * If the checks are successful, the JWT is provided to the profile, otherwise an exception is thrown 👇🏻
     */
    public ProfileDTO login(Auth auth, AppLanguage language) {
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(auth.getEmail(), MD5Util.encode(auth.getPassword()));
        if (optional.isEmpty()) {
            log.warn("email.password.wrong {}", auth.getEmail());
            throw new AppBadException(resourceBundleService.getMessage("email.password.wrong", language));
        }
        ProfileEntity entity = optional.get();
        if (entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            profileService.changeStatus(entity.getId(),ProfileStatus.ACTIVE);
        }
        ProfileDTO dto = new ProfileDTO();
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setJwt(JWTUtil.encodeForSpringSecurity(entity.getEmail(), ProfileRole.ROLE_USER));

        return dto;
    }

    /**
     * this method is used for user registration.
     * If the information entered by the user meets the necessary conditions,
     * a link will be sent to his email, otherwise an exception will be thrown 👇🏻
     */
    public Boolean registration(Registration dto, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                log.warn("Email exists {}", dto.getEmail());
                throw new AppBadException(resourceBundleService.getMessage("email.exists", language));
            }
        }
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 4) {
            log.warn("To many attempt. Please try after 1 minute. {}", dto.getEmail());
            throw new AppBadException(resourceBundleService.getMessage("after.1.minute", language));
        }
        registrationByEmail(dto);
        return true;

    }

    private void registrationByEmail(Registration dto) {
        Random random = new Random();

        String password = String.valueOf(100000 + random.nextInt(900000));

        ProfileEntity entity = new ProfileEntity();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(password));
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);

        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">Please do not share your password with anyone</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" " +
                "<br> Your password " + password +
                "<br>\n";
        text = String.format(text, entity.getFullName());
        mailSender.sendEmail(dto.getEmail(), "Your password", text);

        emailHistoryService.create(dto, text);
    }


}
