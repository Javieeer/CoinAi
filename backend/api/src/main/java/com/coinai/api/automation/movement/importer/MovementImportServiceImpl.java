package com.coinai.api.automation.movement.importer;

import com.coinai.api.automation.movement.dto.MovementDraft;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovementImportServiceImpl
        implements MovementImportService {

    @Override
    public void importMovement(
            MovementDraft draft
    ) {

        log.info(
                "Importando movimiento: {}",
                draft
        );

    }

}