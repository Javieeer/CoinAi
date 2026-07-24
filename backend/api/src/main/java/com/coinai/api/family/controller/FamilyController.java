package com.coinai.api.family.controller;

import com.coinai.api.family.dto.request.CreateFamilyRequest;
import com.coinai.api.family.dto.request.JoinFamilyRequest;
import com.coinai.api.family.dto.request.UpdateFamilyRequest;
import com.coinai.api.family.dto.response.FamilyResponse;
import com.coinai.api.family.service.FamilyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/families")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FamilyResponse create(
            @Valid @RequestBody CreateFamilyRequest request
    ) {

        System.out.println("ENTRO AL CREATE DE FAMILY");

        return familyService.create(request);

    }

    @GetMapping("/me")
    public FamilyResponse getMyFamily() {

        return familyService.getMyFamily();

    }

    @PutMapping
    public FamilyResponse update(
            @Valid @RequestBody UpdateFamilyRequest request
    ) {

        return familyService.update(request);

    }

    @PostMapping("/join")
    public FamilyResponse join(
            @Valid @RequestBody JoinFamilyRequest request
    ) {

        return familyService.join(request);

    }

    @DeleteMapping("/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leave() {

        familyService.leave();

    }

    @DeleteMapping("/members/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(
            @PathVariable UUID memberId
    ) {

        familyService.removeMember(memberId);

    }

}