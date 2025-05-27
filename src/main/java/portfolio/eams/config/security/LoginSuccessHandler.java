package portfolio.eams.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import portfolio.eams.dto.ResponseDto;
import portfolio.eams.entity.system.AccessLog;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.AccessLogRepo;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MyUtil;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    /*
   로그인 성공 시 어떻게 처리할지 정합니다. SecurityConfig 에서 익명함수로 정의해도 좋으나, 내용이 길어질 수 있어 따로 뺍니다.
    */
    private final ObjectMapper mapper;
    private final UserRepo userRepo;
    private final AccessLogRepo acsLogRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("로그인 성공!");

        // 로그인 이력 생성
        User user = userRepo.findByUserId(authentication.getPrincipal().toString()).orElse(null);
        acsLogRepo.save(
                AccessLog.builder()
                        .acsId(user.getUserId())
                        .acsNm(user.getUserNm())
                        .acsSessionId(request.getSession().getId())
                        .acsRoleNm(user.getRole().getRoleNm())
                        .acsIP(request.getRemoteAddr())
                        .acsAgent(request.getHeader("User-Agent"))
                        .build()
        );

        response.setStatus(HttpServletResponse.SC_OK);  // 200
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");

        // message body.
        ResponseDto responseDto = new ResponseDto("00", "login_success", MyUtil.timestamp());
        response.getWriter().write(mapper.writeValueAsString(responseDto));
    }
}
