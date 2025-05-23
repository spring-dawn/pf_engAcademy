package portfolio.eams.config.security.filter;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Arrays;
import java.util.Set;

@lombok.extern.slf4j.Slf4j
@Slf4j
public class CustomRequestWrapper extends HttpServletRequestWrapper {
    /*
    XSS 공격 방어 목적 래퍼
     */

    // 생성자
    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    // header 필터링 제외 대상
    private static final Set<String> SKIPPED_HEADERS = Set.of("If-None-Match", "Authorization", "Content-Type");


    // 재정의
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) return null;

        return SKIPPED_HEADERS.contains(name) ? value : cleanXSS(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) cleanXSS(value);

        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) return null;

        return Arrays.stream(values)
                .map(this::cleanXSS)
                .toArray(String[]::new);
    }


    /**
     * 정규식을 사용한 실제 XSS 필터링 처리
     *
     * @param value 요청 시 입력 데이터
     * @return 필터링 거쳐 가공된 데이터
     */
    private String cleanXSS(String value) {
        if (value == null) return null;
        log.info("asdf 필터 적용 확인 value={}", value);

        // a 태그 사용 차단
        value = value.replaceAll("(?i)<\\s*a\\s+[^>]*>", "");
        value = value.replaceAll("(?i)</a\\s*>", "");

        // XSS 공격 패턴 차단 (script, iframe, 기타 이벤트 핸들러 등)
        value = value.replaceAll("(?i)<\\s*script.*?>.*?</script\\s*>", "&lt;script&gt;");
        value = value.replaceAll("(?i)<\\s*iframe.*?>.*?</iframe\\s*>", "&lt;iframe&gt;");
        value = value.replaceAll("(?i)(javascript|vbscript):", "");
        value = value.replaceAll("(?i)on(error|focus|wheel|pointerenter)", "on$1-removed");

        value = value.replaceAll("(?i)alert\\s*\\(.*?\\)", "");
        value = value.replaceAll("(?i)confirm\\s*\\(.*?\\)", "");
        value = value.replaceAll("(?i)prompt\\s*\\(.*?\\)", "");
        value = value.replaceAll("(?i)eval\\s*\\(.*?\\)", "");

        // 특수문자 엔티티 변환
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;").replaceAll("\"", "&quot;");

        return value;
    }


}

