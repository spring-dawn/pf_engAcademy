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
import portfolio.eams.dto.system.UserDto;
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
    private final CommonCodeRepo cdRepo;


    @Transactional // 영속성 컨텍스트 유지를 위한 트랜잭셔널
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            // 메뉴 생성
            MenuDto dashboard = createMenu4Init("대시보드", "/dashboard", 0, 0, null);

            MenuDto sys = createMenu4Init("시스템관리", "/system", 0, 1, null);
            createMenu4Init("사용자관리", sys.getUrl() + "/user", 1, 0, sys.getUrl());
            createMenu4Init("직급관리", sys.getUrl() + "/role", 1, 1, sys.getUrl());
            createMenu4Init("공통코드관리", sys.getUrl() + "/commonCode", 1, 2, sys.getUrl());

            MenuDto academy = createMenu4Init("학원관리", "/academy", 0, 2, null);
            createMenu4Init("학생관리", academy.getUrl() + "/student", 1, 0, academy.getUrl());
            createMenu4Init("학급관리", academy.getUrl() + "/classroom", 1, 1, academy.getUrl());
            createMenu4Init("학급시간표", academy.getUrl() + "/timetable", 1, 2, academy.getUrl());
            createMenu4Init("성적관리", academy.getUrl() + "/score", 1, 3, academy.getUrl());

            MenuDto operation = createMenu4Init("행정관리", "/operation", 0, 3, null);
            createMenu4Init("공지사항", operation.getUrl() + "/notice", 1, 0, operation.getUrl());
            createMenu4Init("회의록", operation.getUrl() + "/meetingLog", 1, 1, operation.getUrl());
            createMenu4Init("SMS 발송", operation.getUrl() + "/sms", 1, 2, operation.getUrl());

            MenuDto statics = createMenu4Init("통계", "/statics", 0, 4, null);

            // 직급 생성: 유지보수 담당자, 관리자(디폴트), 행정 담당자(팀장), 강사(일반)
            RoleDto sysDev = createRole4Init("유지보수 담당자", 0, 'N', 'Y', "시스템 개발자");
            RoleDto admin = createRole4Init("관리자", 1, 'Y', 'Y', "운영 관리자");
            RoleDto leadTeacher = createRole4Init("팀장", 2, 'Y', 'N', "운영 행정 주 관리자");
            RoleDto teacher = createRole4Init("강사", 3, 'Y', 'N', "일반 강사");


            // 직급-메뉴 관계 생성. 일일이 하나씩 찍자니 너무 많고...
            roleRepo.findAll().forEach(role -> {
                menuRepo.findAll().forEach(menu -> {
                    switch (role.getRoleNm()) {
                        case "유지보수 담당자":
                            linkAcs4Init(role, menu, 'Y', 'Y');
                            break;
                        case "관리자":
                            if (menu.getUrl().endsWith("/commonCode")) {
                                linkAcs4Init(role, menu, 'Y', 'N');
                            } else {
                                linkAcs4Init(role, menu, 'Y', 'Y');
                            }
                            break;
                        case "팀장":
                            if (menu.getUrl().startsWith("/system")) {
                                linkAcs4Init(role, menu, 'N', 'N');
                            } else {
                                linkAcs4Init(role, menu, 'Y', 'Y');
                            }
                            break;
                        case "강사":
                            // 조회 불가, 조회 가능, 조회 가능하지만 쓰기 불가...
                            if (menu.getUrl().startsWith("/system")) {
                                linkAcs4Init(role, menu, 'N', 'N');
                            } else if (menu.getUrl().startsWith("/academy") && !menu.getUrl().endsWith("/score")) {
                                linkAcs4Init(role, menu, 'Y', 'N');
                            } else {
                                linkAcs4Init(role, menu, 'Y', 'Y');
                            }
                            break;
                        default:
                            break;
                    }

                });
            });

            // 샘플 사용자 생성, 직급 할당
            createUser4Init("test1", 'Y', 'Y', "유지보수 담당자");
            createUser4Init("test2", 'Y', 'N', "관리자");
            createUser4Init("test3", 'N', 'N', "팀장");
            createUser4Init("test4", 'N', 'N', "강사");


            // TODO: 공통코드 생성

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("asdf 샘플 데이터 생성 완료");
    }


    // ===================== 이하 샘플 생성용 메서드 =============================================
    @Transactional
    public MenuDto createMenu4Init(String menuNm, String url, Integer depth, Integer sortSeq, String parentUrl) {
        if (menuRepo.existsByUrl(url)) return menuRepo.findByUrl(url).get().toRes();

        return menuRepo.save(
                Menu.builder()
                        .menuNm(menuNm)
                        .url(url)
                        .depth(depth)
                        .sortSeq(sortSeq)
                        .parent(menuRepo.findByUrl(parentUrl).orElse(null))
                        .build()
        ).toRes();
    }

    @Transactional
    public RoleDto createRole4Init(String roleNm, Integer sortSeq, Character visiYn, Character sysDefaultYn, String memo) {
        if (roleRepo.existsByRoleNm(roleNm)) return roleRepo.findByRoleNm(roleNm).get().toRes();

        return roleRepo.save(
                Role.builder()
                        .roleNm(roleNm)
                        .sortSeq(sortSeq)
                        .visibleYn(visiYn)
                        .sysDefaultYn(sysDefaultYn)
                        .memo(memo)
                        .build()
        ).toRes();
    }

    @Transactional
    public void linkAcs4Init(Role role, Menu menu, Character readYn, Character writeYn) {
        if (menuRoleAcsRepo.existsByRoleAndMenu(role, menu)) return;

        menuRoleAcsRepo.save(
                MenuRoleAcs.builder()
                        .role(role)
                        .menu(menu)
                        .readYn(readYn)
                        .writeYn(writeYn)
                        .build()
        );
    }

    @Transactional
    public void createUser4Init(String userId, Character admYn, Character sysDevYn, String roleNm) {
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
                        .sysDevYn(sysDevYn)
                        .tel("010-1111-1111")
                        .email(userId + "@test.com")
                        .joinYmd(now)
                        .pwModYmd(now)
                        .role(role)
                        .build()
        );
    }

}
