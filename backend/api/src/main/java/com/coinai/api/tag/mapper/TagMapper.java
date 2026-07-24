package com.coinai.api.tag.mapper;

import com.coinai.api.tag.dto.request.CreateTagRequest;
import com.coinai.api.tag.dto.response.TagResponse;
import com.coinai.api.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(CreateTagRequest request);

    TagResponse toResponse(Tag tag);

    List<TagResponse> toResponseList(List<Tag> tags);

}