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
            MenuDto sys = createMenu4Init("시스템관리", "/system", 0, 0, null);
            createMenu4Init("사용자관리", sys.getUrl() + "/user", 1, 0, sys.getUrl());
            createMenu4Init("직급관리", sys.getUrl() + "/role", 1, 1, sys.getUrl());
            createMenu4Init("공통코드관리", sys.getUrl() + "/commonCode", 1, 2, sys.getUrl());

            MenuDto academy = createMenu4Init("학원관리", "/academy", 0, 1, null);
            createMenu4Init("학생관리", academy.getUrl() + "/student", 1, 0, academy.getUrl());


            // 직급 생성

            // 직급-메뉴 관계 생성

            // 사용자 생성, 직급 할당

            // 공통코드 생성

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        
        log.info("asdf 메뉴 생성 완료");

    }

    // ===================== 이하 샘플 생성용 메서드 =============================================
    @Transactional
    public MenuDto createMenu4Init(String menuNm, String url, Integer depth, Integer sortSeq, String parentUrl) {
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
    public void linkAcs4Init(Long roleId, Long menuId, Character readYn, Character writeYn) {
        menuRoleAcsRepo.save(
                MenuRoleAcs.builder()
                        .role(roleRepo.findById(roleId).orElse(null))
                        .menu(menuRepo.findById(menuId).orElse(null))
                        .readYn(readYn)
                        .writeYn(writeYn)
                        .build()
        );
    }


    @Transactional
    public UserDto createUser4Init(String userId, Character admYn, String roleNm) {
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
        return userRepo.save(
                        User.builder()
                                .userId(userId)
                                .salt(salt)
                                .userPw(salted)
                                .userNm(userId)
                                .admYn(admYn)
                                .tel("010-1111-1111")
                                .email(userId + "@test.com")
                                .joinYmd(LocalDate.now())
                                .role(role)
                                .build())
                .toRes();
    }

}
