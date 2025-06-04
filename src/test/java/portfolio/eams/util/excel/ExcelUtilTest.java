package portfolio.eams.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import portfolio.eams.dto.common.ExcelDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class ExcelUtilTest {

    @Value("${spring.servlet.multipart.location}")
    String filePath;

    @Autowired
    MessageSource ms;


    @Test
    @DisplayName("readExcel")
    void asdf1() {
        String fileNm1 = "testFile1.xlsx";
        String fileNm2 = "99.png";

        Workbook wb = ExcelUtil.readExcelFile(filePath, fileNm1);

        for (Sheet sheet : wb) {
            for (Row row : sheet) {
                // 줄마다 데이터 읽기
                List<ExcelDto> dpr = new ArrayList<>();
                for (Cell cell : row) {
                    if(!ExcelUtil.hasCell(cell)) continue;
                    // TODO: 병합셀 여부 확인


                    String cellValue = ExcelUtil.getCellValue(cell);

                    dpr.add(new ExcelDto(cell.getColumnIndex(), cellValue));
                }

                for (ExcelDto dto : dpr) {
                    System.out.println("asdf 데이터 확인: "+dto.cellValue());
                }
            }
        }


    }

    @Test
    @DisplayName("스프링 메시지 사용 예시")
    void asdf2() {
        // 기본 디폴트 파일이 하나는 있어야 인식을 하는구나;
//        String errMsg = ms.getMessage("err.test", null, null);
//        String msg = ms.getMessage("test.greeting", null, Locale.KOREA);
        String msg = ms.getMessage("msg.ent.diff", new Object[]{""}, null);

        System.out.println("asdf "+msg);


    }

}