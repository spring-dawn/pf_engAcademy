package portfolio.eams.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import portfolio.eams.entity.system.User;
import portfolio.eams.service.system.CustomUserDetailsService;
import portfolio.eams.service.system.UserService;
import portfolio.eams.util.SHA256Util;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    /*
   실제 인증 정보 처리를 마지막으로 위임 받아 수행하는 곳입니다. 필요한 만큼 여럿 만들어 등록할 수 있음.
    */

    private final CustomUserDetailsService userDetailsService;
//    private final UserService userService;

    @Override
//    @Transactional(readOnly = true)     // FetchType.LAZY
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
         /*
        검증하려는 부분은 throw AuthenticationException 으로 예외를 발생시켜야 합니다.
        try/catch 로 처리하면 틀린 로그인이 제대로 잡히지 않습니다.
         */
        log.info("asdf 로그인 검증 시작!");

        // 클라이언트에서 입력 받은 인증 정보
        String userId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        // DB에 저장돼있는 실제 사용자 정보
        User user = userDetailsService.loadUserByUsername(userId);

        // 계정 유효성 검사. 가장 먼저 검증해야 하는 내용부터 위에 배치.
        if (!user.isEnabled()) throw new DisabledException("Disabled account.");
        if (!user.isAccountNonLocked())
            throw new LockedException("This account has been locked due to more than 5 times authentication failures. Getting new password is recommended.");

        // TODO 패스워드 일치여부 검사
//        boolean validated = SHA256Util.validatePassword(user.getSalt(), password, user.getPassword());
//        if (!validated) {
//            // 인증 실패 횟수 +1
//            userService.countLoginFailure(user.getId());
//            throw new BadCredentialsException("The password is incorrect. Try again plz.");
//        }
//
//        // 인증 성공 시 실패 횟수 초기화
//        if (user.getLoginFailCnt() != 0) userService.initLoginFailure(user.getId());
        // id, (pw 생략), 권한 목록 리턴
//        return new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), user.getAuthorities());
        return new UsernamePasswordAuthenticationToken(user.getUserId(), null, user.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
//        매니저가 사용자 권한 검증 작업을 위임할 프로바이더를 찾을 때, 어떤 검증을 담당하는지 알리는 부분.
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
