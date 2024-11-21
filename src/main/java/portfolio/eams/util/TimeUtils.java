package portfolio.eams.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeUtils {
    /*
    시간 형식과 관련된 메서드 등...
     */
    private TimeUtils() {
        // 유틸 클래스 생성자
    }


    // 시간 타입 형식
    private static final String DT_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";



    /**
     * 디폴트. 현재 시각 출력
     *
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DT_FORMAT_1));
    }

}
