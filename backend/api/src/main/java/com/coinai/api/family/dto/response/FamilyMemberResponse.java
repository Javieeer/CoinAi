package com.coinai.api.family.dto.response;

import com.coinai.api.family.FamilyRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class FamilyMemberResponse {

    private UUID userId;

    private String fullName;

    private FamilyRole role;

    private LocalDateTime joinedAt;

}