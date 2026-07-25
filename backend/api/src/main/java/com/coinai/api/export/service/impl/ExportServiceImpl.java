package com.coinai.api.export.service.impl;

import com.coinai.api.export.service.ExportService;
import com.coinai.api.movement.entity.Movement;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final MovementRepository movementRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public byte[] exportExcel() {

        User user = authenticatedUserService.getCurrentUser();

        List<Movement> movements =
                movementRepository.findByUserIdOrderByMovementDateDesc(
                        user.getId()
                );

        try (
                XSSFWorkbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {

            XSSFSheet sheet = workbook.createSheet("Movements");

            int rowNumber = 0;

            Row header = sheet.createRow(rowNumber++);

            header.createCell(0).setCellValue("Fecha");
            header.createCell(1).setCellValue("Tipo");
            header.createCell(2).setCellValue("Categoría");
            header.createCell(3).setCellValue("Monto");
            header.createCell(4).setCellValue("Descripción");

            for (Movement movement : movements) {

                Row row = sheet.createRow(rowNumber++);

                row.createCell(0)
                        .setCellValue(
                                movement.getMovementDate().toString()
                        );

                row.createCell(1)
                        .setCellValue(
                                movement.getMovementType().name()
                        );

                row.createCell(2)
                        .setCellValue(
                                movement.getCategory().getName()
                        );

                row.createCell(3)
                        .setCellValue(
                                movement.getAmount().doubleValue()
                        );

                row.createCell(4)
                        .setCellValue(
                                movement.getDescription() == null
                                        ? ""
                                        : movement.getDescription()
                        );

            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(output);

            return output.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file.", e);
        }

    }

    @Override
    public byte[] exportPdf() {

        User user = authenticatedUserService.getCurrentUser();

        List<Movement> movements =
                movementRepository.findByUserIdOrderByMovementDateDesc(
                        user.getId()
                );

        try (
                ByteArrayOutputStream output =
                        new ByteArrayOutputStream()
        ) {

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    output
            );

            document.open();

            document.add(new Paragraph("CoinAI - Reporte de movimientos"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);

            table.setWidthPercentage(100);

            table.addCell(new PdfPCell(new Phrase("Fecha")));
            table.addCell(new PdfPCell(new Phrase("Tipo")));
            table.addCell(new PdfPCell(new Phrase("Categoría")));
            table.addCell(new PdfPCell(new Phrase("Monto")));
            table.addCell(new PdfPCell(new Phrase("Descripción")));

            for (Movement movement : movements) {

                table.addCell(
                        movement.getMovementDate().toString()
                );

                table.addCell(
                        movement.getMovementType().name()
                );

                table.addCell(
                        movement.getCategory().getName()
                );

                table.addCell(
                        movement.getAmount().toPlainString()
                );

                table.addCell(
                        movement.getDescription() == null
                                ? ""
                                : movement.getDescription()
                );

            }

            document.add(table);

            document.close();

            return output.toByteArray();

        } catch (DocumentException | IOException e) {

            throw new RuntimeException(
                    "Error generating PDF file.",
                    e
            );

        }

    }

}