package com.coinai.api.family.mapper;

import com.coinai.api.family.dto.request.CreateFamilyRequest;
import com.coinai.api.family.dto.response.FamilyMemberResponse;
import com.coinai.api.family.dto.response.FamilyResponse;
import com.coinai.api.family.entity.Family;
import com.coinai.api.family.entity.FamilyMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FamilyMapper {

    Family toEntity(CreateFamilyRequest request);

    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "members", ignore = true)
    FamilyResponse toResponse(Family family);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "fullName",
            expression = "java(member.getUser().getFirstName() + \" \" + member.getUser().getLastName())")
    FamilyMemberResponse toMemberResponse(FamilyMember member);

    List<FamilyMemberResponse> toMemberResponseList(List<FamilyMember> members);

}