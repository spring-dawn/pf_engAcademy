package portfolio.eams.util.enums;

import lombok.Getter;

@Getter
public class EntityNm {
    /*
    정적 상수로 관리하므로 실제 enum 은 아니지만 기능이 유사하므로 여기 위치.
    enum 일 필요까진 없는 정적 상수로 오버헤드 없이 효율 약상승.
    시스템 전반의 엔티티명. 주로 에러 메시지 등에서 사용.
     */

    private EntityNm(){
        // 기초 프라이빗 생성자
    }
    // test
    public static final String TEST_MSG = "test";

    // SYSTEM
    public static final String USER = "사용자";
    public static final String MENU = "메뉴";
    public static final String ROLE = "역할";
    public static final String AUTH = "권한";

    // BOARD

    // EXAM

    // CLASSES


}
