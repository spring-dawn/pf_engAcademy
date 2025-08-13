//package portfolio.eams.config.security.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Set;
//
//@Slf4j
//@Component // @Autowired 없이 private final~ 형태로 주입하려면 빈 등록 필요
//public class XSSFilter implements Filter {
//    /*
//    XSS(크로스사이트 스크립트 공격) 대응 필터. 가장 기초적인 웹 취약점 보안.
//    스프링 시큐리티 사용 중이라면 addFilter 로 등록하여 사용자 인증 여부에 상관없이 전역 적용합니다.
//
//    init, destroy 등은 전후 처리가 더 필요할 경우 재정의.
//     */
//
//    // 필터 적용 제외 URI
//    private static final Set<String> SKIPPED_URI = Set.of("/test");
//
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String reqUri = req.getRequestURI().trim();
////        log.info("Request URI: {}", reqUri);
//
//        if (SKIPPED_URI.contains(reqUri)) {
//            chain.doFilter(request, response);
//        } else {
//            chain.doFilter(new CustomRequestWrapper(req), response);
//        }
//    }
//
//
//}
