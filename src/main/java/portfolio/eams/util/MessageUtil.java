package portfolio.eams.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component // 빈 주입
@RequiredArgsConstructor
public class MessageUtil {
    /*
    messages.properties 를 편하게 써보겠다는 노력 및 학습...
     */

    private final MessageSource ms;

    /**
     * 별도 인자가 없는 경우
     */
    public String get(String code) {
        return ms.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * {0}, {1}... 등 메시지 인자가 있는 경우
     */
    public String get(String code, Object... args) {
        return ms.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
