package portfolio.eams.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import portfolio.eams.dto.ResponseDto;
import portfolio.eams.util.MyUtil;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    /*
    로그인 인증 실패 시 처리 핸들러. 프로바이더 쪽에서 던진 인증 예외를 따라 처리합니다.
    실제로 사용자 눈에 보이는 안내 메시지이므로 주의.
     */

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.info("로그인 실패!");
//        String errMsg = "";
        StringBuilder errMsg = new StringBuilder();

        if (exception instanceof UsernameNotFoundException) {
            // 사용자 조회 실패
            errMsg.append(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.USER));
        } else if (exception instanceof BadCredentialsException) {
            // 비밀번호 불일치
            errMsg.append(InfoMsg.PW_INCORRECT.getMsg());
        } else if (exception instanceof DisabledException) {
            // 미사용 계정
            errMsg.append(InfoMsg.ACCOUNT_DISABLED.getMsg());
        } else if (exception instanceof LockedException) {
            errMsg.append(InfoMsg.ACCOUNT_LOCKED.getMsg());
        } else {
            // 예상치 못한 오류 발생. 로깅.
            errMsg.append(InfoMsg.COMMON.getMsg());
            exception.printStackTrace();
        }

        // msg
        response.setStatus(HttpServletResponse.SC_OK);  // 200
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");

        // TODO: 내부 코드 체계를 간단하게 잡아야 할 듯.
        ResponseDto res = new ResponseDto("01", errMsg.toString(), MyUtil.timestamp());
        response.getWriter().write(mapper.writeValueAsString(res));
    }
}
