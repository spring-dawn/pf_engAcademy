package portfolio.eams;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    /*
    시스템 기초 샘플 데이터 자동생성 클래스
    어플리케이션 최초 빌드 시 1회 실행됩니다
     */

    @Transactional // 영속성 컨텍스트 유지를 위한 트랜잭셔널
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
