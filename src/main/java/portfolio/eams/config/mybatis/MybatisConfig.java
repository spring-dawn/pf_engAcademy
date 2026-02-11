//package portfolio.eams.config.mybatis;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = "portfolio.eams.repo.mybatis")
//public class MybatisConfig {
//    /*
//    jpa, querydsl 등과 함께 혼용할 mybatis 기본설정 클래스.
//    기본적인 서비스 로직은 주로 jpa 로 처리하되 복잡한 통계, 엑셀 다운로드용 데이터, 대용량 데이터 배치 처리 등에 사용
//    1개 api 에 jpa 와 mybatis 를 섞어쓰지 말 것. 원칙적으로 단일 사용할 것.
//
//    프로젝트 폴더 하위에는 매퍼 인터페이스, /resources/mapper 하위에는 실제 xml 쿼리 파일 위치.
//    basePackages 범위는 매퍼 인터페이스 경로.
//     */
//
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource);
//
//        // XML 매퍼 경로 지정 TODO: 초기에는 테스트용으로 /mapper/*.xml 로 바로 진행
//        factoryBean.setMapperLocations(
////                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml")
//                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
//        );
//
//        return factoryBean.getObject();
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
//        return new SqlSessionTemplate(factory);
//    }
//}
//
//
