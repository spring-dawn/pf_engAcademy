package portfolio.eams;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.dto.system.RoleDto;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.MenuRoleAcs;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.*;
import portfolio.eams.util.SHA256Util;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    /*
    시스템 기초 샘플 데이터 자동생성 클래스
    어플리케이션 최초 빌드 시 1회 실행됩니다
     */

    private final MenuRepo menuRepo;
    private final RoleRepo roleRepo;
    private final MenuRoleAcsRepo menuRoleAcsRepo;
    private final UserRepo userRepo;

    @Transactional // 영속성 컨텍스트 유지를 위한 트랜잭셔널
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // menu: 권한 체크가 필요한 정식 url. 사이드바에 출력, tab: 목적별 분류, sub: 기능성 파생 화면, gnb: 로그인만 하면 사용 가능
        String menuType1 = "MENU";
        String menuType2 = "TAB";
        String menuType3 = "SUB";
        String menuType4 = "GNB";

        try {
            // 메뉴 생성. 대시보드 0
            createMenu4Init("대시보드", "/dashboard", menuType4, 'Y', 1, 0, null);
            createMenu4Init("개인정보 조회", "/myPage", menuType4, 'Y', 1, 0, null);
            createMenu4Init("비밀번호 변경", "/changePw", menuType4, 'Y', 1, 0, null);

            // 시스템 1
            MenuDto sys = createMenu4Init("시스템", "/system", menuType1, 'N', 1, 1, null);
            createMenu4Init("사용자관리", sys.getUrl() + "/user", menuType1, 'Y', 2,0, sys.getUrl());
            createMenu4Init("권한관리", sys.getUrl() + "/role", menuType1, 'Y', 2,1, sys.getUrl());
            MenuDto commonCode = createMenu4Init("공통코드관리", sys.getUrl() + "/commonCode", menuType1, 'N', 2, 2, sys.getUrl());
            createMenu4Init("목록", commonCode.getUrl()+"/list", menuType2, 'Y', 3, 0, commonCode.getUrl());
            createMenu4Init("사용처", commonCode.getUrl()+"/usage", menuType2, 'Y', 3, 1, commonCode.getUrl());

            // 학원운영 2
            MenuDto academy = createMenu4Init("학원운영", "/academy", menuType1, 'N', 1, 2, null);

            // 1) 수강생관리(관리자)
            MenuDto admStudent = createMenu4Init("수강생 관리", academy.getUrl() + "/student", menuType1, 'Y', 2, 0, academy.getUrl());
            createMenu4Init("수강생 등록", admStudent.getUrl()+"/edit", menuType3, 'Y', 3, 2, admStudent.getUrl());

            // 2) 수강생찾기~탭화면
            MenuDto studentFind = createMenu4Init("수강생 찾기", academy.getUrl() + "/student/find", menuType1, 'N', 2, 1, academy.getUrl());
            createMenu4Init("상세", studentFind.getUrl()+"/detail", menuType2, 'Y', 3, 0, studentFind.getUrl());
            createMenu4Init("성적 관리", studentFind.getUrl()+"/score", menuType2, 'Y', 3, 1, studentFind.getUrl());
            createMenu4Init("보호자 상담", studentFind.getUrl()+"/consultation", menuType2, 'Y', 3, 2, studentFind.getUrl());

            // 3) 학급관리(관리자) 
            MenuDto admClass = createMenu4Init("학급 관리", academy.getUrl() + "/class", menuType1, 'N', 2, 3, academy.getUrl());
            createMenu4Init("목록", admClass.getUrl()+"/list", menuType2, 'Y', 3, 0, admClass.getUrl());
            createMenu4Init("인원 배치", admClass.getUrl()+"/members", menuType2, 'Y', 3, 1, admClass.getUrl());
            createMenu4Init("수업 목록", admClass.getUrl()+"/curriculum", menuType2, 'Y', 3, 2, admClass.getUrl());
            createMenu4Init("시간표", admClass.getUrl()+"/timetable", menuType2, 'Y', 3, 3, admClass.getUrl());

            // 행정사무 3
            MenuDto oper = createMenu4Init("행정", "/operation", menuType1, 'N', 1, 3, null);
            createMenu4Init("공지사항", oper.getUrl()+"/notice", menuType1, 'Y', 2, 0, oper.getUrl());
            createMenu4Init("회의록", oper.getUrl()+"/meetingLog", menuType1, 'Y', 2, 1, oper.getUrl());

            // sms 안내 4
            createMenu4Init("SMS 안내", "/sms", menuType1, 'Y', 1, 4, null);

            // 통계 시각화 5
            createMenu4Init("통계", "/statics", menuType1, 'Y', 1, 5, null);


            // 직급 생성: 관리자(디폴트), 개발자, 행정 담당자(팀장), 강사(일반)
            RoleDto admin = createRole4Init("관리자", 1, 'Y', null);
            RoleDto dev = createRole4Init("개발자", 0, 'N', "시스템 개발자, 유지보수 담당자");
            RoleDto leader = createRole4Init("팀장", 2, 'N', "행정 관리자");
            RoleDto teacher = createRole4Init("강사", 3, 'Y', "일반 강사");

            // 직급-메뉴 관계 생성.
            roleRepo.findAll().forEach(role -> {
                menuRepo.findAll().forEach(menu -> {
                    switch (role.getRoleNm()) {
                        case "개발자":
                            linkAcs4Init(role, menu, 'Y', 'Y', 'Y');
                            break;
                        case "관리자":
                            if (!menu.getUrl().contains("/commonCode")){
                                linkAcs4Init(role, menu, 'Y', 'Y', 'Y');
                            }else {
                                linkAcs4Init(role, menu, 'N', 'N', 'N');
                            }
                            break;
                        case "팀장":
                            if (!menu.getUrl().startsWith("/system")) {
                                linkAcs4Init(role, menu, 'Y', 'Y', 'N');
                            }
                            break;
                        case "강사":
                            //
                            if(!menu.getUrl().startsWith("/system")){
                                linkAcs4Init(role, menu, 'N', 'N', 'N');
                            }
                            break;
                        default:
                            break;
                    }

                });
            });

            // 샘플 사용자 생성, 직급 할당
            createUser4Init("test1", 'Y', "개발자");
            createUser4Init("test2", 'Y',  "관리자");
            createUser4Init("test3", 'N',  "팀장");
            createUser4Init("test4", 'N',  "강사");

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("asdf 샘플 데이터 생성 완료");
    }


    // ===================== 이하 샘플 생성용 메서드 =============================================

    /**
     * 샘플 메뉴 데이터 생성
     * @param menuNm 메뉴출력명
     * @param url 고유한 url
     * @param menuType menu: 사이드바, tab: 탭, 기능성 파생: sub, 퀵: gnb
     * @param scrYn 최하단 실제 화면여부
     * @param depth 메뉴깊이. 최대 3.
     * @param sortSeq 정렬순서
     * @param parentUrl 상위 url
     * @return menu dto
     */
    @Transactional
    public MenuDto createMenu4Init(String menuNm, String url, String menuType, Character scrYn, Integer depth, Integer sortSeq, String parentUrl) {
        if (menuRepo.existsByUrl(url)) return menuRepo.findByUrl(url).get().toRes();

        return menuRepo.save(
                Menu.builder()
                        .menuNm(menuNm)
                        .url(url)
                        .menuType(menuType)
                        .scrYn(scrYn)
                        .depth(depth)
                        .sortSeq(sortSeq)
                        .parent(menuRepo.findByUrl(parentUrl).orElse(null))
                        .build()
        ).toRes();
    }

    @Transactional
    public RoleDto createRole4Init(String roleNm, Integer sortSeq, Character sysYn, String rmrk) {
        if (roleRepo.existsByRoleNm(roleNm)) return roleRepo.findByRoleNm(roleNm).get().toRes();

        return roleRepo.save(
                Role.builder()
                        .roleNm(roleNm)
                        .sortSeq(sortSeq)
                        .sysYn(sysYn)
                        .rmrk(rmrk)
                        .build()
        ).toRes();
    }

    @Transactional
    public void linkAcs4Init(Role role, Menu menu, Character actYn, Character cuYn, Character dYn) {
        if (menuRoleAcsRepo.existsByRoleAndMenu(role, menu)) return;

        menuRoleAcsRepo.save(
                MenuRoleAcs.builder()
                        .role(role)
                        .menu(menu)
                        .actYn(actYn)
                        .cuYn(cuYn)
                        .dYn(dYn)
                        .build()
        );
    }

    @Transactional
    public void createUser4Init(String userId, Character admYn, String roleNm) {
        if (userRepo.existsByUserId(userId)) return;

        String salt = "";
        String salted = "";
        try {
            SHA256Util.PwDto pwDto = SHA256Util.createPw(userId);

            salt = pwDto.salt();
            salted = pwDto.salted();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

        Role role = roleRepo.findByRoleNm(roleNm)
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.ROLE)));
        LocalDate now = LocalDate.now();

        userRepo.save(
                User.builder()
                        .userId(userId)
                        .salt(salt)
                        .userPw(salted)
                        .userNm(userId)
                        .admYn(admYn)
                        .tel("010-1111-1111")
                        .email(userId + "@test.com")
                        .joinYmd(now)
                        .pwModYmd(now)
                        .role(role)
                        .build()
        );
    }

}
