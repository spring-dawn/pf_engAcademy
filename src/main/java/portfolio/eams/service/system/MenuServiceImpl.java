package portfolio.eams.service.system;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import portfolio.eams.dto.system.AuthDto;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Auth;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.RoleAuth;
import portfolio.eams.repo.system.AuthRepo;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.util.MyUtil;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepo repo;
    private final RoleRepo roleRepo;
    private final AuthRepo authRepo;

    // Character[] 은 stream 사용 불가, Arrays.stream() 으로 감싸야 함
    private static final Character[] AUTH_TYPE_ARR = {'C', 'R', 'U', 'D'};


    @Cacheable(value = "mymenu", key = "#authentication.getAuthorities().toArray()[0].toString()")
    public List<MenuDto> selectMyMenu(Authentication auth) {
//        1) 사용자 인증 정보 수신. 정보 없음 == 미인증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        2) role 정보 확인.
        String roleNm = authentication.getAuthorities().toArray()[0].toString();

//        3) role 내부 메뉴 확인
        Role role = roleRepo.findByRoleNm(roleNm)
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.ROLE)));
        // TODO: 권한에 useYn 이 필요한가?
//        if(role.getUseYn().equals('N')) throw new IllegalStateException("");
        List<RoleAuth> authList = role.getRoleAuthList();

//        4) 권한 있는 최상위 메뉴 우선, 이후 재귀 호출로 하위 메뉴까지 추려냄.
        List<Menu> root = new ArrayList<>();
        Set<Long> allowed = new HashSet<>();

        for (RoleAuth ra : authList) {
            // 권한 있는 모든 메뉴 id 수집
            Menu menu = ra.getAuth().getMenu();
            if(menu.getUseYn().equals('N')) continue;
            allowed.add(menu.getId());

            // R(ead) 조회권한이 있는 최상위 메뉴를 root 목록에 추리기
            if (menu.getParent() != null) continue;
            Character type = ra.getAuth().getType();
            if (type.equals('R')) root.add(menu);
        }

//        5) TODO: 중복 한 번 더 털기... 근데 중복이 왜 나오지??
        List<Menu> menus = filterAllowedMenu(root, allowed);
//        Set<Menu> menuSet = new HashSet<>(menus);

        return menus.stream().map(Menu::toRes).toList();
    }


    @CacheEvict(value = "mymenu", key = "#roleNm")
    public void deleteMenuCacheByRoleNm(String roleNm) {
        log.info(roleNm + "권한 메뉴에 @CacheEvict 가 실행됩니다. " + MyUtil.timestamp());
    }


    public List<Menu> filterAllowedMenu(List<Menu> rootMenus, Set<Long> allowed) {
//        1)
        List<Menu> myMenu = new ArrayList<>();

//        2) 최상위 -> 하위 메뉴로 계층을 내려가며 탐색, List 에 추려내기.
        for (Menu m : rootMenus) {
            if (allowed.contains(m.getId())) {
                // 하위 메뉴 재귀
                if (!ObjectUtils.isEmpty(m.getChildren())) {
                    m.addChildren(filterAllowedMenu(m.getChildren(), allowed));
                }
                myMenu.add(m);
            }
        }

//        3)
        return myMenu;
    }


    @Transactional
    public MenuDto createMenu4Init(MenuDto.Req req) {
//        1) is exists already?
        Menu isExist = repo.findByUrl(req.getUrl()).orElse(null);
        if(isExist != null) return isExist.toRes();
        if (!StringUtils.hasText(req.getUrl())) throw new IllegalArgumentException(InfoMsg.NPE.getMsg());

//        2) save
        Menu menu = repo.save(
                Menu.builder()
                        .menuNm(req.getMenuNm())
                        .url(req.getUrl())
                        .order(req.getOrder())
                        .parent(req.getParentUrl() == null ? null : repo.findByUrl(req.getParentUrl()).orElse(null))
                        .build()
        );

//        3) create authorities following menu
        Arrays.stream(AUTH_TYPE_ARR)
                .map(type -> authRepo.save(Auth.builder()
                                .type(type)
                                .menu(menu)
                                .build())
                        .toRes())
                .toList();

//        4) res
        log.info("메뉴 생성: " + req.getMenuNm());
        return menu.toRes();
    }


    public MenuDto selectMenu(String url) {
        return repo.findByUrl(url)
                .map(Menu::toRes)
                .orElse(null);
    }
}
