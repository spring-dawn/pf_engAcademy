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

    // 공통 인자
    private final String value;
}
