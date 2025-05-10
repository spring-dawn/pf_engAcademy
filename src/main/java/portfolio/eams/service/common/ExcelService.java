package portfolio.eams.service.common;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import portfolio.eams.dto.common.ExcelDto;
import portfolio.eams.util.excel.ExcelUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelService {
    /*
    임시 테스트용 서비스 클래스
     */


    /**
     * 엑셀 생성 예시
     *
     * @param response 응답
     */
    public void writeExcel_test(HttpServletResponse response) {
//        1) 엑셀 생성
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

//        2) 내용
        XSSFRow row;
        int rowNo = 0;
        int cellNo = 0;

        // 첫 줄에 헤더 쓰고 꾸미기
        row = sheet.createRow(rowNo);
        row.setHeightInPoints((float) 30);

        CellStyle basic = ExcelUtil.customStyle(wb, true, true, false);
        Font bold20 = ExcelUtil.customFont(wb, "굴림체",true,  20);
        basic.setFont(bold20);
//
        String[] header = {"가", "나", "다", "라"};
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
            row.getCell(i).setCellStyle(basic);
        }

        // 둘째줄
        row = sheet.createRow(++rowNo);
        row.createCell(cellNo++).setCellValue("A");
        row.createCell(cellNo++).setCellValue("B");
        row.createCell(cellNo++).setCellValue("C");
        row.createCell(cellNo).setCellValue("D");

        //셋째줄. 병합
        row = sheet.createRow(++rowNo);

        // 빈 엑셀에 처음 만들 때는 createCell() 로 셀 객체를 만들어야만 병합도 할 수 있다
        cellNo = 0;
        row.createCell(cellNo++);
        row.createCell(cellNo++);
        row.createCell(cellNo++);
        row.createCell(cellNo);
        sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, row.getFirstCellNum(), row.getLastCellNum() - 1));

        CellStyle mergingStyle = ExcelUtil.customStyle(wb, false, true, true);
        Font pt14 = ExcelUtil.customFont(wb, "맑은 고딕", false,  14);
        mergingStyle.setFont(pt14);

        row.getCell(0).setCellValue("(주)에이테크");
        row.getCell(0).setCellStyle(mergingStyle);

//        넷째줄. 데이터 포맷 적용.  병합
        row = sheet.createRow(++rowNo);

        row.createCell(0);
        row.createCell(1);
        row.createCell(2);
        row.createCell(3);
        sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, row.getFirstCellNum(), row.getLastCellNum() - 1));

        row.getCell(0).setCellValue(Double.parseDouble("123456789"));
//        row.createCell(0).setCellValue(Double.parseDouble("35.23452432240003"));

        // 서식.
        basic.setDataFormat(wb.createDataFormat().getFormat(ExcelUtil.FORMAT_COMMA));
//        basic.setDataFormat(wb.createDataFormat().getFormat(ExcelUtil.FORMAT_DECIMAL2PLACES));
        for (Cell cell : row) {
            cell.setCellStyle(basic);
        }
//        3) 엑셀 생성, 출력
        String fileNm = "ExcelUtilTest";
        ExcelUtil.writeExcelFile(response, wb, fileNm);
    }

}
