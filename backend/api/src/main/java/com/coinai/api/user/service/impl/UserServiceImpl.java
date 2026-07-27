package com.coinai.api.user.service.impl;

import com.coinai.api.auth.service.EmailVerificationService;
import com.coinai.api.cloudinary.ImageStorageService;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.request.UpdateProfileRequest;
import com.coinai.api.user.dto.response.RegisterResponse;
import com.coinai.api.user.dto.response.UpdateProfileResponse;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.exception.EmailAlreadyExistsException;
import com.coinai.api.user.mapper.UserMapper;
import com.coinai.api.user.repository.UserRepository;
import com.coinai.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final AuthenticatedUserService authenticatedUserService;
    private final ImageStorageService imageStorageService;
    private final EmailVerificationService emailVerificationService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = mapper.toEntity(request);

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        repository.save(user);

        emailVerificationService.createVerificationToken(user);

        return mapper.toResponse(user);

    }

    @Override
    public UpdateProfileResponse updateProfile(
            UpdateProfileRequest request
    ) {

        User user = authenticatedUserService.getCurrentUser();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPreferredCurrency(request.getPreferredCurrency());
        user.setTimezone(request.getTimezone());

        repository.save(user);

        return UpdateProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .preferredCurrency(user.getPreferredCurrency())
                .timezone(user.getTimezone())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();

    }

    @Override
    public UpdateProfileResponse uploadProfilePicture(
            MultipartFile file
    ) {

        User user = authenticatedUserService.getCurrentUser();

        String url = imageStorageService.upload(file);

        user.setProfilePictureUrl(url);

        repository.save(user);

        return UpdateProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePictureUrl(user.getProfilePictureUrl())
                .preferredCurrency(user.getPreferredCurrency())
                .timezone(user.getTimezone())
                .build();

    }
    
}