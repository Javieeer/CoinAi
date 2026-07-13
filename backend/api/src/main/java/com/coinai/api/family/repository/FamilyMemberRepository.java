package com.coinai.api.family.repository;

import com.coinai.api.family.entity.Family;
import com.coinai.api.family.entity.FamilyMember;
import com.coinai.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {

    List<FamilyMember> findByFamilyId(UUID familyId);

    Optional<FamilyMember> findByFamilyIdAndUserId(
            UUID familyId,
            UUID userId
    );

    boolean existsByFamilyIdAndUserId(
            UUID familyId,
            UUID userId
    );

    Optional<FamilyMember> findByUserId(UUID userId);

    List<FamilyMember> findByFamilyOrderByJoinedAtAsc(Family family);

    List<FamilyMember> findByFamily(Family family);

    List<FamilyMember> findByUser(User user);

}