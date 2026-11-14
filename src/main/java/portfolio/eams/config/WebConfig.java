package portfolio.eams.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*
    WebConfig, WebMvcConfigurer
    Spring MVC의 웹 레이어 전반을 커스텀하고 제어하는 중앙 제어탑 클래스
    주로 필터/인터셉터, 외부 파일 업로드 경로 지정, cors 정책, 포워딩(리다이렉트 포함) 등 웹과 관련한 전역 기능을 모아두고 확장 가능
    
    261113 당장은 리다이렉트 용도.
     */


    /**
     * 특정 url 필요에 따라 리다이렉트
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addRedirectViewController("/system/commonCode", "/system/commonCode/list");

    }
}
