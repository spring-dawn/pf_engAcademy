package portfolio.eams.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    /*
    api 관리용 Swagger3.0 연동
    spring boot 3.3.x 버전 이후로 의존성 변경 주의.

    공식문서 https://springdoc.org/#Introduction
     */

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Spring boot 3.3.5 - Swagger")
                .description("포트폴리오1 REST api 조회 문서")
                .version("1.0.0");
    }


}
