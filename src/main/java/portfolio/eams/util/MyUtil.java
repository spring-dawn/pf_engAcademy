package portfolio.eams.util;

import lombok.extern.slf4j.Slf4j;
import portfolio.eams.util.enums.InfoMsg;

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
    private static final String DT_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";


    /**
     * 디폴트. 현재 시각 출력
     *
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DT_FORMAT_1));
    }


    /**
     * enum 동적 재사용 메시지 불러오기
     *
     * @param type enum Msg 클래스에 선언된 것중 사용할 양식
     * @param str  %s 자리에 들어갈 특정 문자열 혹은 엔티티명 ex) 사용자, 권한, 코드...
     * @return 조합된 메시지 문자열
     */
    public static String getEnum(InfoMsg type, String str) {
        return String.format(type.getMsg(), str);
    }
}
