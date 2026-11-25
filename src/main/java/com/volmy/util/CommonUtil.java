package com.volmy.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class CommonUtil {

    public static boolean isCellNotBlank(Cell cell) {
        return cell != null && cell.getCellType() != CellType.BLANK;
    }

    public static int calculatePercentage(double currentValue, double totalValue) {
        return (int) (currentValue / totalValue * 100);
    }

    public static Date toDate(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    public static LocalDate toLocalDate(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .orElse(null);
    }
}
