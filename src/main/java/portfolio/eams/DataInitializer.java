package portfolio.eams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.repo.system.AuthRepo;
import portfolio.eams.service.system.MenuService;
import portfolio.eams.service.system.RoleService;
import portfolio.eams.service.system.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
//@Transactional(readOnly = true) // 이거 맞나?
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    /*
    어플리케이션 실행 시 메뉴, 권한, 역할 등 시스템 기초 데이터 생성
    JPA 의 ddl-auto 설정과 별도 실행이므로 운영 시에는 주의합니다.
     */

    private final AuthRepo authRepo;

    private final UserService userService;
    private final RoleService roleService;
    private final MenuService menuService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 이미 존재하는 경우 새로 실행하지 않는 조건

        try {
            if (menuService.selectMenu("/dashboard") != null) return;

            // 메뉴 생성
            MenuDto mDashboard = menuService.createMenu(new MenuDto.Req("/dashboard", "대시보드", 0, null));

            // system
            MenuDto menu0 = menuService.createMenu(new MenuDto.Req("/system", "시스템관리", 0, null));
            MenuDto menu0_0 = menuService.createMenu(new MenuDto.Req(menu0.getUrl() + "/user", "사용자관리", 0, menu0.getUrl()));
            MenuDto menu0_1 = menuService.createMenu(new MenuDto.Req(menu0.getUrl() + "/role", "직급관리", 1, menu0.getUrl()));
            MenuDto menu0_2 = menuService.createMenu(new MenuDto.Req(menu0.getUrl() + "/code", "코드관리", 2, menu0.getUrl()));

            // academic operation
            MenuDto menu1 = menuService.createMenu(new MenuDto.Req("/academy", "학원 운영", 1, null));
            MenuDto menu1_0 = menuService.createMenu(new MenuDto.Req(menu1.getUrl() + "/student", "학생관리", 0, menu1.getUrl()));
            MenuDto menu1_1 = menuService.createMenu(new MenuDto.Req(menu1.getUrl() + "/class", "학급관리", 1, menu1.getUrl()));
            MenuDto menu1_2 = menuService.createMenu(new MenuDto.Req(menu1.getUrl() + "/course", "수업관리", 2, menu1.getUrl()));
            MenuDto menu1_3 = menuService.createMenu(new MenuDto.Req(menu1.getUrl() + "/score", "성적관리", 3, menu1.getUrl()));

            // administration
            MenuDto menu2 = menuService.createMenu(new MenuDto.Req("/manage", "행정 운영", 2, null));
            MenuDto menu2_0 = menuService.createMenu(new MenuDto.Req(menu2.getUrl() + "/notice", "공지사항", 0, menu2.getUrl()));
            MenuDto menu2_1 = menuService.createMenu(new MenuDto.Req(menu2.getUrl() + "/meeting", "회의록", 1, menu2.getUrl()));
            MenuDto menu2_2 = menuService.createMenu(new MenuDto.Req(menu2.getUrl() + "/sms", "SMS 발송", 2, menu2.getUrl()));
            MenuDto menu2_3 = menuService.createMenu(new MenuDto.Req(menu2.getUrl() + "/pay", "수강료 납부", 3, menu2.getUrl()));

            // quarter scheduling
            MenuDto menu3 = menuService.createMenu(new MenuDto.Req("/schedule", "수업 일정", 2, null));

            // inventory
            MenuDto menu4 = menuService.createMenu(new MenuDto.Req("/inventory", "비품관리", 3, null));
            MenuDto menu4_0 = menuService.createMenu(new MenuDto.Req(menu4.getUrl() + "/stock", "비품관리", 0, menu4.getUrl()));
            MenuDto menu4_1 = menuService.createMenu(new MenuDto.Req(menu4.getUrl() + "/order", "발주서", 1, menu4.getUrl()));
            MenuDto menu4_2 = menuService.createMenu(new MenuDto.Req(menu4.getUrl() + "/adjust", "재고조정", 2, menu4.getUrl()));
            MenuDto menu4_3 = menuService.createMenu(new MenuDto.Req(menu4.getUrl() + "/status", "재고현황", 3, menu4.getUrl()));

            // analysis
            MenuDto menu5 = menuService.createMenu(new MenuDto.Req("/analy", "통계", 4, null));

        } catch (Exception e) {
            log.error("메뉴 생성 중 오류가 있습니다.");
        }


        // TODO: 메뉴별 권한 생성
        try{
            log.info("메뉴별 권한 생성");

        }catch (Exception e){
            log.error("권한 생성 중 오류가 있습니다.");
        }

        // TODO: 역할 생성

        // TODO: 사용자 생성

        // TODO: 시스템코드 생성

    }

}