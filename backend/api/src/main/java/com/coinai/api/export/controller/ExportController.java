package com.coinai.api.export.controller;

import com.coinai.api.export.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportExcel() {

        byte[] file = exportService.exportExcel();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("coinai.xlsx")
                                .build()
                                .toString()
                )
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(file);

    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf() {

        byte[] file = exportService.exportPdf();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("coinai.pdf")
                                .build()
                                .toString()
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);

    }

}