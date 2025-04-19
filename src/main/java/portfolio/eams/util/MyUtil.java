package portfolio.eams.util;

import lombok.extern.slf4j.Slf4j;
import portfolio.eams.util.enums.InfoMsg;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MyUtil {
    /*
    시간 형식, 이넘, 문자열 자르기 등 각종 유틸
     */
    private MyUtil() {
        // 유틸 클래스 생성자
    }

    // 시간 타입 형식
    private static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";


    /**
     * 디폴트. 현재 시각 출력
     *
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_1));
    }


    /**
     * 파일 확장자 추출
     * @param fileNm 파일명.확장자 형태
     * @return .확장자 e.g) .xlsx
     */
    public static String getFileExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

}
