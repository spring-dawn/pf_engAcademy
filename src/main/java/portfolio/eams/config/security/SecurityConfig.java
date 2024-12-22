package portfolio.eams.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*
    스프링 시큐리티 설정
     */


    // Security Filter Chain 을 Bean 으로 등록해서 사용. 메서드 체이닝, 람다식 활용.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // url 문자열 기반 로그인 인증 유무 설정. 구체적인 요청은 윗줄, 추상적인 요청은 아랫줄에 작성.
                .authorizeHttpRequests(config -> config
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/**",

                                // 정적 자원
                                "/css",
                                "/js",
                                "/img"
                        ).permitAll()
                        .anyRequest().authenticated() // TODO: 사용자 권한별 접근 필터 별도 추가
                )
                // 내부망 상정, 폼로그인 사용.
                .formLogin(config -> config
                                .loginPage("/signin").permitAll()
                                .loginProcessingUrl("/api/signin")
                                .usernameParameter("userId")
                        // TODO: 로그인 성공, 실패 핸들러 추가
                )
                // 로그아웃 시 세션 제거
                .logout(config -> config
                        .deleteCookies("JSESSIONID_EAMS", "remember-me")
                )
                // 자동 로그인
                .rememberMe(config -> config
                        .rememberMeParameter("autoLogin")
                )
                // 세션
                .sessionManagement(config -> config
                        .maximumSessions(2) // 최대 허용 가능 세션 수 (-1 : 무제한)
                        .maxSessionsPreventsLogin(false) // true : 로그인 제한, false(default) : 기존 세션 만료
                        .expiredUrl("/signin")
                )
        ;

        // 최종 빌드
        return http.build();
    }


}
