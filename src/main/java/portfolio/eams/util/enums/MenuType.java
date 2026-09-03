package portfolio.eams.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuType {
    M1("MENU"),
    M2("TAB"),
    M3("SUB"),
    M4("GNB"),
    ;

    // 생성자 인자
    private final String value;

    /**
     * 대소문자 무시하고 메뉴타입과 일치하는지 확인
     * @param menuTypeStr MENU, TAB 등의 분류코드
     * @return T: 일치
     */
    public boolean isEquals(String menuTypeStr){
        return value.equalsIgnoreCase(menuTypeStr);
    }
}
