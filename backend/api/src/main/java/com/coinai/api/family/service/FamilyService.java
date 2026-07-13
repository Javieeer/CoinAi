package com.coinai.api.family.service;

import com.coinai.api.family.dto.request.CreateFamilyRequest;
import com.coinai.api.family.dto.request.JoinFamilyRequest;
import com.coinai.api.family.dto.request.UpdateFamilyRequest;
import com.coinai.api.family.dto.response.FamilyResponse;

public interface FamilyService {

    FamilyResponse create(CreateFamilyRequest request);

    FamilyResponse getMyFamily();

    FamilyResponse update(UpdateFamilyRequest request);

    FamilyResponse join(JoinFamilyRequest request);

    void leave();

}