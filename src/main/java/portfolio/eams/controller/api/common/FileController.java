package portfolio.eams.controller.api.common;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.eams.service.common.ExcelService;

@Slf4j
@RestController
@RequestMapping("/api/test/file")
@RequiredArgsConstructor
public class FileController {
    /*
    파일 기능 Test
     */

    private final ExcelService excelService;

    @GetMapping("/excelTest")
    public void writeExcel(HttpServletResponse response) {
        excelService.writeExcel_test(response);
    }


}
