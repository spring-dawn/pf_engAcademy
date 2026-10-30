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
        /*
        메뉴타입은 권한검사 방식 및 메뉴 배치에 따라 분류.

        bar: 권한 체크가 필요한 정식 url. 메뉴바에 출력
        tab: 같은 엔티티(메뉴) 안 목적별 분류
        sub: 기능성 파생 화면. 주로 상세조회, 쓰기 화면. 상위메뉴의 권한 상속할 것.
        common: 로그인만 하면 사용가능. 별도 접근권 검사 없음.
         */

        String mType1 = "BAR";
        String mType2 = "TAB";
        String mType3 = "SUB";
        String mType4 = "DEFAULT"; // default.

        try {
            // 메뉴 생성. 대시보드 0
            createMenu4Init("대시보드", "/dashboard", mType4, 'Y', 1, 0, null);
            createMenu4Init("내 정보", "/myPage", mType4, 'Y', 1, 0, null);
            createMenu4Init("비밀번호 변경", "/changePw", mType4, 'Y', 1, 0, null);

            // 시스템 1
            MenuDto sys = createMenu4Init("시스템관리", "/system", mType1, 'N', 1, 1, null);
            createMenu4Init("사용자", sys.getUrl() + "/user", mType1, 'Y', 2,0, sys.getUrl());
            createMenu4Init("권한", sys.getUrl() + "/role", mType1, 'Y', 2,1, sys.getUrl());
            MenuDto commonCode = createMenu4Init("공통코드", sys.getUrl() + "/commonCode", mType1, 'N', 2, 2, sys.getUrl());
            createMenu4Init("코드목록", commonCode.getUrl()+"/list", mType2, 'Y', 3, 0, commonCode.getUrl());
            createMenu4Init("사용처", commonCode.getUrl()+"/usage", mType2, 'Y', 3, 1, commonCode.getUrl());

            // 학생관리
            MenuDto studentMng = createMenu4Init("학생관리", "/student", mType1, 'n', 1, 2, null);
            MenuDto stuMngList = createMenu4Init("학생목록", studentMng.getUrl()+"/list", mType1, 'y', 2, 0, studentMng.getUrl());
            createMenu4Init("학생 상세", stuMngList.getUrl()+"/read", mType3, 'Y', 3, 0, stuMngList.getUrl());
            createMenu4Init("힉생 등록", stuMngList.getUrl()+"/write", mType3, 'Y', 3, 1, stuMngList.getUrl());

            createMenu4Init("성적관리", studentMng.getUrl()+"/score", mType1, 'Y', 2, 1, studentMng.getUrl());
            createMenu4Init("상담내역", studentMng.getUrl()+"/counsel", mType1, 'Y', 2, 2, studentMng.getUrl());

            // 학급관리
            MenuDto classMng = createMenu4Init("학급관리", "/class", mType1, 'N', 1, 3, null);
            createMenu4Init("학급목록", classMng.getUrl()+"/list", mType1, 'Y', 2, 0, classMng.getUrl());
            createMenu4Init("수업배치", classMng.getUrl()+"/curriculum", mType1, 'Y', 2, 1, classMng.getUrl());
            MenuDto timetable = createMenu4Init("시간표", classMng.getUrl() + "/timetable", mType1, 'Y', 2, 2, classMng.getUrl());
            createMenu4Init("시간표 상세", timetable.getUrl()+"/read", mType3, 'Y', 3, 0, timetable.getUrl());
            createMenu4Init("시간표 등록", timetable.getUrl()+"/write", mType3, 'Y', 3, 1, timetable.getUrl());

            // 행정
            MenuDto oper = createMenu4Init("행정", "/operation", mType1, 'N', 1, 4, null);
            createMenu4Init("공지사항", oper.getUrl()+"/notice", mType1, 'Y', 2, 0, oper.getUrl());
            createMenu4Init("회의록", oper.getUrl()+"/meetingLog", mType1, 'Y', 2, 1, oper.getUrl());

            // sms 안내
            createMenu4Init("SMS", "/sms", mType1, 'Y', 1, 5, null);

            // 통계 시각화
            createMenu4Init("통계/시각화", "/statics", mType1, 'Y', 1, 6, null);


            // 직급 생성: 관리자(디폴트), 개발자, 행정 담당자(팀장), 강사(일반)
            RoleDto admin = createRole4Init("관리자", 1, 'Y', "시스템 운영자");
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
