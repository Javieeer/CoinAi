package com.coinai.api.family.dto.response;

import com.coinai.api.family.FinancialMode;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyResponse {

    private UUID id;

    private String name;

    private String description;

    private String inviteCode;

    private FinancialMode financialMode;

    private UUID createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<FamilyMemberResponse> members;

}