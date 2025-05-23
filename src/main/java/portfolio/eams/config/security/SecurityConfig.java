package portfolio.eams.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import portfolio.eams.config.security.filter.XSSFilter;


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /*
    스프링 시큐리티 설정
     */

    // 로그인 핸들러
    private final LoginSuccessHandler successHandler;
    private final LoginFailureHandler failureHandler;

    // 필터
    private final XSSFilter xssFilter;


    // Security Filter Chain 을 Bean 으로 등록해서 사용. 메서드 체이닝, 람다식 활용.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 네트워크 설정; 문법이 또 바뀌었다
                .csrf(AbstractHttpConfigurer::disable)

                // url 문자열 기반 로그인 인증 유무 설정. 구체적인 요청은 윗줄, 추상적인 요청은 아랫줄에 작성.
                .authorizeHttpRequests(config -> config
                        .requestMatchers(
                                "/api/signup",
                                "/api/signin",
                                "/api/test/**",
//                                "/api/**",

                                //
                                "/swagger-ui/**",
                                "/v3/api-docs/**",

                                // 정적 자원
                                "/css/**",
                                "/js/**",
                                "/img/**"

                        ).permitAll()
                        .anyRequest().authenticated() // TODO: 사용자 권한별 접근 필터 별도 추가
                )
                // 내부망 상정, 폼로그인 사용.
                .formLogin(config -> config
                        .loginPage("/signin").permitAll()
                        .loginProcessingUrl("/api/signin")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                )
                // 로그아웃 시 세션 제거
                .logout(config -> config
                        .logoutUrl("/api/signout")
                        .deleteCookies("JSESSIONID_EAMS", "remember-me")
                )
                // 자동 로그인, 디폴트 2주 지속.
                .rememberMe(config -> config
                        .rememberMeParameter("autoLogin")
                        .key("autoLogin")
//                        .tokenValiditySeconds(90 * 24 * 60 * 60) // 3달 유효 기간 설정
                )
                // 세션
                .sessionManagement(config -> config
                        .maximumSessions(2) // 최대 허용 가능 세션 수 (-1 : 무제한)
                        .maxSessionsPreventsLogin(false) // true : 로그인 제한, false(default) : 기존 세션 만료
                        .expiredUrl("/signin")
                )

                // 필터 적용
                .addFilterBefore(xssFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        // 최종 빌드
        return http.build();
    }


}
