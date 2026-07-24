package com.coinai.api.tag.service;

import com.coinai.api.tag.dto.request.CreateTagRequest;
import com.coinai.api.tag.dto.response.TagResponse;

import java.util.List;

public interface TagService {

    TagResponse create(CreateTagRequest request);

    List<TagResponse> findAll();

}