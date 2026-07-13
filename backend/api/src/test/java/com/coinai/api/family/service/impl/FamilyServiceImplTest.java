package com.coinai.api.family.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class FamilyServiceImplTest {
    
    @Mock
    private FamilyRepository familyRepository;

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @Mock
    private FamilyMapper familyMapper;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private FamilyServiceImpl familyService;

    @Test
    void shouldCreateFamilySuccessfully() { //OK

        UUID userId = UUID.randomUUID();
        UUID familyId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        CreateFamilyRequest request = new CreateFamilyRequest();
        request.setName("Familia Zapata");
        request.setDescription("Familia principal");

        Family family = Family.builder()
                .id(familyId)
                .name("Familia Zapata")
                .description("Familia principal")
                .financialMode(FinancialMode.INDIVIDUAL)
                .inviteCode("ABC123XYZ")
                .createdBy(user)
                .build();

        FamilyMember member = FamilyMember.builder()
                .family(family)
                .user(user)
                .role(FamilyRole.ADMIN)
                .build();

        FamilyResponse response = FamilyResponse.builder()
                .id(familyId)
                .name("Familia Zapata")
                .description("Familia principal")
                .financialMode(FinancialMode.INDIVIDUAL)
                .inviteCode("ABC123XYZ")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        when(familyMapper.toEntity(request))
                .thenReturn(family);

        when(familyRepository.save(any(Family.class)))
                .thenReturn(family);

        when(familyMemberRepository.save(any(FamilyMember.class)))
                .thenReturn(member);

        when(familyMapper.toResponse(family))
                .thenReturn(response);

        FamilyResponse result = familyService.create(request);

        assertEquals("Familia Zapata", result.getName());
        assertEquals("Familia principal", result.getDescription());
        assertEquals(FinancialMode.INDIVIDUAL, result.getFinancialMode());

        verify(familyRepository).save(any(Family.class));
        verify(familyMemberRepository).save(any(FamilyMember.class));
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyBelongsToFamily() { //OK

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        CreateFamilyRequest request = new CreateFamilyRequest();
        request.setName("Nueva familia");

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(userId))
                .thenReturn(Optional.of(new FamilyMember()));

        assertThrows(
                FamilyAlreadyExistsException.class,
                () -> familyService.create(request)
        );

        verify(familyRepository, never()).save(any());
        verify(familyMemberRepository, never()).save(any());
    }

    @Test
    void shouldUpdateFamilySuccessfully() { //OK

        UUID userId = UUID.randomUUID();
        UUID familyId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Family family = Family.builder()
                .id(familyId)
                .name("Old")
                .description("Old description")
                .financialMode(FinancialMode.INDIVIDUAL)
                .build();

        FamilyMember member = FamilyMember.builder()
                .user(user)
                .family(family)
                .role(FamilyRole.ADMIN)
                .build();

        UpdateFamilyRequest request = new UpdateFamilyRequest();
        request.setName("New");
        request.setDescription("New description");
        request.setFinancialMode(FinancialMode.SHARED);

        FamilyResponse response = FamilyResponse.builder()
                .id(familyId)
                .name("New")
                .description("New description")
                .financialMode(FinancialMode.SHARED)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(userId))
                .thenReturn(Optional.of(member));

        when(familyRepository.save(any(Family.class)))
                .thenReturn(family);

        when(familyMapper.toResponse(family))
                .thenReturn(response);

        FamilyResponse result = familyService.update(request);

        assertEquals("New", result.getName());
        assertEquals(FinancialMode.SHARED, result.getFinancialMode());

        verify(familyRepository).save(any(Family.class));

    }

    @Test
    void shouldThrowExceptionWhenFamilyNotFoundOnUpdate() { //OK

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        UpdateFamilyRequest request = new UpdateFamilyRequest();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                FamilyNotFoundException.class,
                () -> familyService.update(request)
        );

        verify(familyRepository, never()).save(any());

    }

    @Test
    void shouldJoinFamilySuccessfully() { //OK

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Family family = Family.builder()
                .id(UUID.randomUUID())
                .name("Family")
                .financialMode(FinancialMode.INDIVIDUAL)
                .inviteCode("ABC123")
                .build();

        JoinFamilyRequest request = new JoinFamilyRequest();
        request.setInviteCode("ABC123");

        FamilyResponse response = FamilyResponse.builder()
                .id(family.getId())
                .name("Family")
                .financialMode(FinancialMode.INDIVIDUAL)
                .inviteCode("ABC123")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        when(familyRepository.findByInviteCode("ABC123"))
                .thenReturn(Optional.of(family));

        when(familyMapper.toResponse(family))
                .thenReturn(response);

        FamilyResponse result = familyService.join(request);

        assertEquals("Family", result.getName());

        verify(familyMemberRepository).save(any(FamilyMember.class));

    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyBelongsToFamilyOnJoin() { //OK

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        JoinFamilyRequest request = new JoinFamilyRequest();
        request.setInviteCode("ABC123");

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(new FamilyMember()));

        assertThrows(
                FamilyAlreadyExistsException.class,
                () -> familyService.join(request)
        );

        verify(familyMemberRepository, never()).save(any());

    }

    @Test
    void shouldThrowExceptionWhenInviteCodeDoesNotExist() { //OK

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        JoinFamilyRequest request = new JoinFamilyRequest();
        request.setInviteCode("INVALID");

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(user.getId()))
                .thenReturn(Optional.empty());

        when(familyRepository.findByInviteCode("INVALID"))
                .thenReturn(Optional.empty());

        assertThrows(
                FamilyNotFoundException.class,
                () -> familyService.join(request)
        );

    }

    @Test
        void shouldLeaveFamilySuccessfully() { //OK

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        Family family = Family.builder()
                .id(UUID.randomUUID())
                .build();

        FamilyMember member = FamilyMember.builder()
                .user(user)
                .family(family)
                .role(FamilyRole.MEMBER)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(member));

        when(familyMemberRepository.findByFamilyOrderByJoinedAtAsc(family))
                .thenReturn(List.of());

        familyService.leave();

        verify(familyMemberRepository).delete(member);
        verify(familyRepository).delete(family);

        }

    @Test
    void shouldThrowExceptionWhenUserDoesNotBelongToFamily() { //OK

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(familyMemberRepository.findByUserId(user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                FamilyNotFoundException.class,
                () -> familyService.leave()
        );

        verify(familyMemberRepository, never()).save(any());

    }

}
