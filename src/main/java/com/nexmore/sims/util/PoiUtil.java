package com.nexmore.sims.util;

import com.nexmore.sims.excel.vo.ExcelVO;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoiUtil {

    public static List<ExcelVO> parseExcel(byte[] fileBytes) throws IOException {
        List<ExcelVO> dataList = new ArrayList<>();

        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 가져오기

            Iterator<Row> rowIterator = sheet.iterator();

            // 첫 번째 행은 헤더이므로 무시하기
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // 열 데이터 읽기
                Iterator<Cell> cellIterator = row.cellIterator();
                ExcelVO ExcelVO = new ExcelVO(); // VO 객체 생성

                int cellIndex = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            ExcelVO.setEQP_ID(getCellValueAsString(cell));
                            break;
                        case 1:
                            ExcelVO.setSVC_NAME(getCellValueAsString(cell));
                            break;
                        case 2:
                            ExcelVO.setIP_ADDRESS(getCellValueAsString(cell));
                            break;
                        case 3:
                            ExcelVO.setUSER_ID(getCellValueAsString(cell));
                            break;
                        default:
                            // 예외 처리 또는 다른 작업 수행
                    }
                    cellIndex++;
                }

                dataList.add(ExcelVO);
            }
        }

        return dataList;
    }

    // 셀 타입별 변환
    public static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (cell.getNumericCellValue() == (long) cell.getNumericCellValue()) {
                    return String.valueOf((long) cell.getNumericCellValue());
                } else {
                    DecimalFormat df = new DecimalFormat("#.##########");
                    return df.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}
