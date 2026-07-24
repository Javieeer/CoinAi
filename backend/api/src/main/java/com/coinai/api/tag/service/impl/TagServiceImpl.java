package com.coinai.api.tag.service.impl;

import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.tag.dto.request.CreateTagRequest;
import com.coinai.api.tag.dto.response.TagResponse;
import com.coinai.api.tag.entity.Tag;
import com.coinai.api.tag.exception.TagAlreadyExistsException;
import com.coinai.api.tag.mapper.TagMapper;
import com.coinai.api.tag.repository.TagRepository;
import com.coinai.api.tag.service.TagService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public TagResponse create(CreateTagRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (tagRepository.existsByUserIdAndNameIgnoreCase(
                user.getId(),
                request.getName()
        )) {
            throw new TagAlreadyExistsException();
        }

        Tag tag = tagMapper.toEntity(request);

        tag.setUser(user);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());

        tag = tagRepository.save(tag);

        return tagMapper.toResponse(tag);

    }

    @Override
    public List<TagResponse> findAll() {

        User user = authenticatedUserService.getCurrentUser();

        return tagMapper.toResponseList(
                tagRepository.findByUserIdOrderByNameAsc(user.getId())
        );

    }

}