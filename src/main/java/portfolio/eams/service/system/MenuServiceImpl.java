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
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.RoleAuth;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.util.MyUtil;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepo repo;
    private final RoleRepo roleRepo;


    @Cacheable(value = "mymenu", key = "#authentication.getAuthorities().toArray()[0].toString()")
    public List<MenuDto> selectMyMenu(Authentication auth) {
//        1) 사용자 인증 정보 수신. 정보 없음 == 미인증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        2) role 정보 확인.
        String roleNm = authentication.getAuthorities().toArray()[0].toString();

//        3) role 내부 메뉴 확인
        Role role = roleRepo.findByRoleNm(roleNm)
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.ROLE)));
        List<RoleAuth> authList = role.getAuthList();

//        4) 권한 있는 최상위 메뉴 우선, 이후 재귀 호출로 하위 메뉴까지 추려냄.
        List<Menu> root = new ArrayList<>();
        List<Long> allowed = new ArrayList<>();

        for (RoleAuth ra : authList) {
            // 권한 있는 최상위 메뉴 우선 -> root 리스트 추리기
            // authList 에 존재하며 패런트가 없는 메뉴가 최상위.
            Character writeYn = ra.getAuth().getWriteYn();
            Menu menu = ra.getAuth().getMenu();

            if (writeYn.equals('Y') && menu.getParent() == null) root.add(menu);
        }

//        5) 중복 한 번 더 털기... 근데 중복이 왜 나오지??
        List<Menu> menus = filterAllowedMenu(root, allowed);
        Set<Menu> menuSet = new HashSet<>(menus);

        return menuSet.stream().map(Menu::toRes).toList();
    }


    @CacheEvict(value = "mymenu", key = "#roleNm")
    public void deleteMenuCacheByRoleNm(String roleNm) {
        log.info(roleNm + "권한 메뉴에 @CacheEvict 가 실행됩니다. " + MyUtil.timestamp());
    }


    public List<Menu> filterAllowedMenu(List<Menu> rootMenus, List<Long> allowed) {
//        1)
        List<Menu> allowedList = new ArrayList<>();

//        2)
        for (Menu m : rootMenus) {
            // 상위 메뉴가 허용돼야 탭도 통과되므로 이중 검사 없이 바로 추가.
            if (m.getUseYn().equals('Y')) allowedList.add(m);
            if (m.getUseYn().equals('Y') && allowed.contains(m.getId())) {
                //
                if (m.getChildren() != null && !m.getChildren().isEmpty()) {
                    m.addChildren(filterAllowedMenu(m.getChildren(), allowed));
                }
                allowedList.add(m);
            }
        }
//        3)
        return allowedList;
    }


    @Transactional
    public MenuDto createMenu(MenuDto.Req req) {
//        1) is exists already?
        if(repo.existsByUrl(req.getUrl())) throw new EntityExistsException(InfoMsg.ALREADY_EXISTS.format(EntityNm.MENU));

//        2) save
        Menu parentMenu = repo.findByUrl(req.getParentUrl()).orElse(null);
        Menu menu = repo.save(
                Menu.builder()
                        .menuNm(req.getMenuNm())
                        .url(req.getUrl())
                        .order(req.getOrder())
                        .parent(parentMenu)
                .build()
        );

//        3) res
        return menu.toRes();
    }
}