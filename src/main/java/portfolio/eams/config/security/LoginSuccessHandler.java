package portfolio.eams.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import portfolio.eams.util.MyUtil;

import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
     /*
    로그인 성공 시 어떻게 처리할지 정합니다. SecurityConfig 에서 익명함수로 정의해도 좋으나, 내용이 길어질 수 있어 따로 뺍니다.
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        objectmapper 안 쓰고 그냥 쓰면 되지 않나...? 안 되네 json 변환해야 하네;
        response.setStatus(HttpServletResponse.SC_OK);  // 200
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");

        // TODO: message body.
//        this.responseLoginSuccess.setTimestamp(MyUtil.timestamp());
//        response.getWriter().write(objectMapper.writeValueAsString(this.responseLoginSuccess));
    }
}
