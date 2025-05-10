package portfolio.eams.util.excel;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import portfolio.eams.util.MyUtil;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ExcelUtil {
    /*
    5.x.x 버전의 apache poi 엑셀 처리 유틸리티 클래스
    일반적으로 XSSF(.xlsx) 처리.
    대용량 엑셀인 경우 SXSSF 로 메모리 누수 방지. 단, 섬세한 조작은 불가.
     */

    // 엑셀 파일 확장자 xlsx(XSSF), xls(HSSF)
    private static final String FILE_EXT_XLSX = ".xlsx";
    private static final String FILE_EXT_XLS = ".xls";

    //
    public static final String FORMAT_D1 = "#,##0.0";  // 소수 첫째자리까지
    public static final String FORMAT_D2 = "#,##0.00";  // 소수 둘째자리까지
    public static final String FORMAT_COMMA = "#,##0";  // 천 단위로 콤마
    

    /**
     * 엑셀 파일 읽기
     *
     * @param filePath 파일 저장 경로. 설정파일에 명시 추천.
     * @param fileNm   파일명+확장자(.xlsx, .xls)
     * @return Workbook
     */
    public static Workbook readExcelFile(String filePath, String fileNm) {
        // validation
        if (!StringUtils.hasText(fileNm)) throw new IllegalArgumentException("파일명 오류");

        String fileExt = MyUtil.getFileExt(fileNm);
        if (!fileExt.equals(FILE_EXT_XLSX) && !fileExt.equals(FILE_EXT_XLS))
            throw new IllegalArgumentException("확장자가 xlsx, xls 가 아님");

        // load file
        File file = new File(filePath + File.separator + fileNm);
        if (!file.exists()) throw new IllegalArgumentException("파일 404");

        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(file);
        } catch (IOException | InvalidFormatException e) {
            log.error("엑셀 워크북 생성에 오류가 있습니다 err={}", e.getMessage());
        }

        return wb;
    }


  
    // 보류
//    public static Workbook readExcelFile(String filePath, String fileNm, String password) {
//        // validation
//        if (StringUtils.hasText(fileNm)) throw new IllegalArgumentException("");
//
//        String fileExt = MyUtil.getFileExt(fileNm);
//        if (!fileExt.equals(FILE_EXT_XLSX) && !fileExt.equals(FILE_EXT_XLS))
//            throw new IllegalArgumentException("");
//
//        // load file
//        File file = new File(filePath + File.separator + fileNm);
//        if (!file.exists()) throw new IllegalArgumentException("");
//
//        Workbook wb = null;
//        try {
//            // readOnly 옵션은 적용 안 함
//            wb = WorkbookFactory.create(file, password);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//
//        return wb;
//    }


    /**
     * 엑셀 파일 출력(브라우저 다운로드)
     * 이미 블록 안에 파일 출력이 있으므로 다운로드 컨트롤러 api 타입도 void 여야 합니다.
     * 클라이언트 요청 시엔 비동기가 아닌 url 쿼리스트링을 사용합니다.
     *
     * @param response 응답 헤더. 컨텐트 타입 명시 필요.
     * @param workbook 출력할 엑셀(.xlsx) 객체
     * @param fileNm   출력할 엑셀 파일의 확장자 없는 파일명
     */
    public static void writeExcelFile(HttpServletResponse response, Workbook workbook, String fileNm) {
        if (!StringUtils.hasText(fileNm)) throw new IllegalArgumentException("");

        try (Workbook wb = workbook) {
//            1) 파일명 + 확장자, 인코딩
            fileNm += FILE_EXT_XLSX;
            String encFileNm = URLEncoder.encode(fileNm, StandardCharsets.UTF_8).replace("\\+", "%20");

//            2) 응답 헤더 작성
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encFileNm + "\";");

//            3) 완성된 엑셀 출력, 스트림 닫기
            wb.write(response.getOutputStream());
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
        }
    }


    // TODO: 셀 병합 여부 파악, boolean

    /**
     * 셀 데이터 유효 여부
     *
     * @param cell 셀
     * @return T: 유효, F: null/"" 등 유효하지 않은 셀
     */
    public static boolean hasCell(Cell cell) {
        return cell != null && cell.getCellType() != CellType.BLANK && cell.getCellType() != CellType.ERROR;
    }


    /**
     * 셀 데이터 읽기
     * @param cell hasCell 을 통해 유효 여부를 검증한 셀
     * @return String result
     */
    public static String getCellValue(Cell cell) {
        // hasCell 을 선행할 것. 셀이 유효하지 않으면 시도조차 하지 않고 건너뛴다.
        // 에러 셀을 제외하면 크게 4가지: String, Numeric, Formula, Date
        // 수식 적용 셀을 제외하면 그냥 getStringCellValue 로 뽑는 게 맞다 um...
        String result;

        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC:
                result = String.valueOf(cell.getNumericCellValue());
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            default:
                result = cell.getStringCellValue();
                break;
        }

        return result;
    }



    /*
    ==================================================================================
    서식(스타일) 유틸. 주로 .xlsx 대상.
    서식 객체는 한 번 만들어두고 재사용합니다. 적용 시 중복되지 않게 주의.
    병합 셀의 경우 범위 안 모든 셀을 순회하며 스타일 적용해야 합니다.

    스타일, 폰트는 별도 분리 권장.
    디폴트 설정 외 글자색 등 일부 서식은 경우에 따라 새로 커스텀.
    ==================================================================================
     */


    /**
     * 셀 스타일 객체 생성
     *
     * @param wb            대상 워크북(엑셀)
     * @param hasBorder     전체 테두리 적용 여부. 디폴트: 검정, 얇은 선
     * @param isAlignCenter 데이터 정렬. 디폴트: 왼쪽
     * @param isWrapText    데이터 개행 여부. T: 줄바꿈, F: 셀맞춤
     * @return 신규 CellStyle 객체
     */
    public static CellStyle customStyle(Workbook wb, boolean hasBorder, boolean isAlignCenter, boolean isWrapText) {
        CellStyle cs = wb.createCellStyle();

        // 셀 테두리 TODO: 테두리 커스텀은 쫌... 그래
        if (hasBorder) {
            cs.setBorderLeft(BorderStyle.THIN);
            cs.setBorderRight(BorderStyle.THIN);
            cs.setBorderTop(BorderStyle.THIN);
            cs.setBorderBottom(BorderStyle.THIN);
        }

        // 셀 데이터 수직 정렬
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        // 셀 데이터 수평 정렬: 디폴트 좌측
        if (isAlignCenter) {
            cs.setAlignment(HorizontalAlignment.CENTER);
        }

        // 데이터 길이에 따른 줄바꿈 or 셀맞춤 여부
        if (isWrapText) {
            cs.setWrapText(true);
        } else {
            cs.setShrinkToFit(true);
        }

        return cs;
    }


    /**
     * 커스텀 폰트 객체 생성
     *
     * @param wb     대상 워크북(엑셀)
     * @param fontNm 글꼴명 e.g) "맑은 고딕"
     * @param isBold T: 굵게, F: 일반
     * @param pt     글씨 크기
     * @return Font 색상 디폴트 블랙
     */
    public static Font customFont(Workbook wb, String fontNm, boolean isBold, int pt) {
        Font font = wb.createFont();
        // 글꼴
        font.setFontName(fontNm);
        // 굵게
        font.setBold(isBold);
        // 크기. 단위 pt
        font.setFontHeightInPoints((short) pt);

        return font;
    }


    // TODO: 귀찮으니 그냥 CS + Font 합친 디폴트 셀 스타일 버전도 만든다.

    /**
     * 셀 스타일에 데이터 서식 적용
     * @param wb 대상 워크북
     * @param cs CellStyle
     * @param formatNm 소수점 등 poi enum 포맷 문자열 e.g.) 소수 첫째자리 "#,##0.0"
     * @return 데이터 서식이 추가된 CellStyle
     */
    public static CellStyle setDataFormat(Workbook wb, CellStyle cs, String formatNm) {
        short formatter = wb.createDataFormat().getFormat(formatNm);
        cs.setDataFormat(formatter);

        return cs;
    }




}
