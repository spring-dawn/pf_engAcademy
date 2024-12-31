package portfolio.eams.config.jsonConvert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    /*
    빈으로 등록하며 전역에서 간편하게 사용.
    정적 유틸리티 클래스를 따로 작성하여 사용하는 것도 가능.
     */

    @Bean
    public ObjectMapper objectMapper() {
        // 기본 설정
        return new ObjectMapper();

        // 커스텀 케이스
        // return new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
