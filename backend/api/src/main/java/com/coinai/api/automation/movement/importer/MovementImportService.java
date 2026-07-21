package com.coinai.api.automation.movement.importer;

import com.coinai.api.automation.movement.dto.MovementDraft;

public interface MovementImportService {

    void importMovement(MovementDraft draft);

}