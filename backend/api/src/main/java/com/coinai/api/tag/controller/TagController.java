package com.coinai.api.tag.controller;

import com.coinai.api.tag.dto.request.CreateTagRequest;
import com.coinai.api.tag.dto.response.TagResponse;
import com.coinai.api.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse create(
            @Valid @RequestBody CreateTagRequest request
    ) {

        return tagService.create(request);

    }

    @GetMapping
    public List<TagResponse> findAll() {

        return tagService.findAll();

    }

}