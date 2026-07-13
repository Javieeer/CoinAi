package com.coinai.api.family.service.impl;

import com.coinai.api.family.FamilyRole;
import com.coinai.api.family.FinancialMode;
import com.coinai.api.family.dto.request.CreateFamilyRequest;
import com.coinai.api.family.dto.request.JoinFamilyRequest;
import com.coinai.api.family.dto.request.UpdateFamilyRequest;
import com.coinai.api.family.dto.response.FamilyResponse;
import com.coinai.api.family.entity.Family;
import com.coinai.api.family.entity.FamilyMember;
import com.coinai.api.family.exception.FamilyAlreadyExistsException;
import com.coinai.api.family.exception.FamilyNotFoundException;
import com.coinai.api.family.mapper.FamilyMapper;
import com.coinai.api.family.repository.FamilyMemberRepository;
import com.coinai.api.family.repository.FamilyRepository;
import com.coinai.api.family.service.FamilyService;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyMapper familyMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public FamilyResponse create(CreateFamilyRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (familyMemberRepository.findByUserId(user.getId()).isPresent()) {
            throw new FamilyAlreadyExistsException();
        }

        Family family = familyMapper.toEntity(request);

        family.setInviteCode(generateInviteCode());
        family.setFinancialMode(FinancialMode.INDIVIDUAL);
        family.setCreatedBy(user);
        family.setCreatedAt(LocalDateTime.now());
        family.setUpdatedAt(LocalDateTime.now());

        family = familyRepository.save(family);

        FamilyMember owner = FamilyMember.builder()
                .family(family)
                .user(user)
                .role(FamilyRole.ADMIN)
                .joinedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        familyMemberRepository.save(owner);

        return familyMapper.toResponse(family);

    }

    private String generateInviteCode() {

        String code;

        do {
            code = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 10)
                    .toUpperCase();
        } while (familyRepository.findByInviteCode(code).isPresent());

        return code;

    }

    @Override
    public FamilyResponse getMyFamily() {

        User user = authenticatedUserService.getCurrentUser();

        FamilyMember member = familyMemberRepository.findByUserId(user.getId())
                .orElseThrow(FamilyNotFoundException::new);

        return buildFamilyResponse(member.getFamily());

    }

    private FamilyResponse buildFamilyResponse(Family family) {

        List<FamilyMember> members = familyMemberRepository.findByFamily(family);

        FamilyResponse response = familyMapper.toResponse(family);

        response.setMembers(
                familyMapper.toMemberResponseList(members)
        );

        return response;

    }

    @Override
    public FamilyResponse update(UpdateFamilyRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FamilyMember member = familyMemberRepository.findByUserId(user.getId())
                .orElseThrow(FamilyNotFoundException::new);

        Family family = member.getFamily();

        family.setName(request.getName());
        family.setDescription(request.getDescription());
        family.setFinancialMode(request.getFinancialMode());
        family.setUpdatedAt(LocalDateTime.now());

        family = familyRepository.save(family);

        return buildFamilyResponse(family);

    }

    @Override
    public FamilyResponse join(JoinFamilyRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (familyMemberRepository.findByUserId(user.getId()).isPresent()) {
            throw new FamilyAlreadyExistsException();
        }

        Family family = familyRepository.findByInviteCode(request.getInviteCode())
                .orElseThrow(FamilyNotFoundException::new);

        FamilyMember member = FamilyMember.builder()
                .family(family)
                .user(user)
                .role(FamilyRole.MEMBER)
                .joinedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        familyMemberRepository.save(member);

        family.setUpdatedAt(LocalDateTime.now());
        familyRepository.save(family);

        return buildFamilyResponse(family);

    }

    @Override
    public void leave() {

        User user = authenticatedUserService.getCurrentUser();

        FamilyMember member = familyMemberRepository.findByUserId(user.getId())
                .orElseThrow(FamilyNotFoundException::new);

        Family family = member.getFamily();

        FamilyRole role = member.getRole();

        familyMemberRepository.delete(member);

        List<FamilyMember> remainingMembers =
                familyMemberRepository.findByFamilyOrderByJoinedAtAsc(family);

        if (remainingMembers.isEmpty()) {
            familyRepository.delete(family);
            return;
        }

        if (role == FamilyRole.ADMIN) {

            FamilyMember newOwner = remainingMembers.getFirst();

            newOwner.setRole(FamilyRole.ADMIN);

            familyMemberRepository.save(newOwner);

        }

        family.setUpdatedAt(LocalDateTime.now());

        familyRepository.save(family);

    }

}