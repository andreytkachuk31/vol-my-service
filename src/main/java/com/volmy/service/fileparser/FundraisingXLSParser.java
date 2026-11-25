package com.volmy.service.fileparser;

import com.volmy.model.Donor;
import com.volmy.model.Fundraising;
import com.volmy.service.importcontext.ImportContext;
import com.volmy.service.importcontext.ImportStatus;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.volmy.util.CommonUtil.calculatePercentage;
import static com.volmy.util.CommonUtil.isCellNotBlank;

@Singleton
public class FundraisingXLSParser implements FileParser<Fundraising> {

    @Inject
    private ImportContext importContext;

    @Override
    public List<Fundraising> parse(CompletedFileUpload file, String importId) {
        List<Fundraising> fundraisings = new ArrayList<>();

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(is);

            int totalNumberOfSheets = workbook.getNumberOfSheets();
            for (int i = 1; i < totalNumberOfSheets; i++) {
                this.importContext.update(importId, calculatePercentage(i, totalNumberOfSheets), ImportStatus.IN_PROGRESS);
                Sheet sheet = workbook.getSheetAt(i);

                Fundraising fundraising = new Fundraising();
                fundraising.setName(sheet.getSheetName());
                fundraising.setTag("Військовий");
                fundraising.setStatus("Completed");
                for (Row row : sheet) {
                    Cell donorNameCell = row.getCell(0);
                    Cell donorSumCell = row.getCell(1);
                    if (isCellNotBlank(donorNameCell) && isCellNotBlank(donorSumCell)) {
                        String donorName = donorNameCell.getStringCellValue();
                        Double donorSum = donorSumCell.getNumericCellValue();
                        if ("Итого".equals(donorName)) {
                            fundraising.setProgressPercent(100);
                            fundraising.setCurrentSum(donorSum);
                            fundraising.setTotalSum(donorSum);
                        } else {
                            Donor donor = new Donor();
                            donor.setSum(donorSum);
                            donor.setName(donorName);
                            fundraising.addDonor(donor);
                        }
                    }
                }
                fundraisings.add(fundraising);
            }
        } catch (IOException ignored) {
        }

        return fundraisings;
    }
}
