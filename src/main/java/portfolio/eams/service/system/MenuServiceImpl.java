package portfolio.eams.service.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.RoleAuth;
import portfolio.eams.repo.system.MenuRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepo repo;

    @Cacheable(value = "mymenu", key = "#authentication.getAuthorities().toArray()[0].toString()")
    public List<MenuDto> selectMyMenu(Authentication auth) {
//        1) 사용자 인증 정보 수신. 정보 없음 == 미인증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        2) role 정보 확인.
        String roleNm = authentication.getAuthorities().toArray()[0].toString();


        return null;

//        //        1) 로그인 사용자 정보 수신 (AUTHENTICATION 에 유저정보가 없다. == 로그인하지 않음)
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
////        2) 사용자 인증 정보에서 부여된 컨테이너 확인. 1사용자 1롤.
//        String roleNm = authentication.getAuthorities().toArray()[0].toString();
//        Role role = roleRepo.findByName(roleNm).orElseThrow(EntityNotFoundException::new);
//
////        3) 인증된 사용자에게 승인된 메뉴 목록 조회
//        List<Menu> menus = new ArrayList<>();
//        for (RoleAuth ra : role.getAuthList()) {
//            if (ra.getAuth().getWriteYn().equals("N")) menus.add(ra.getAuth().getMenu());
//        }
//
////        4) 승인된 메뉴 목록 안에서 1레벨 2레벨 순서 정리: 전체(상위 메뉴(하위 메뉴 리스트))
//        List<Menu> myMenus = new ArrayList<>();
//        for (Menu m : menus) {
//            // 상위 && 사용중
//            if (m.getParent() == null && m.getUseYn().equals("Y")) {
//                // 하위 && 사용중
//                if (m.getChildren() != null && !m.getChildren().isEmpty()) {
//                    List<Menu> children = new ArrayList<>();
//                    for (Menu c : m.getChildren()) {
//                        if (menus.contains(c) && c.getUseYn().equals("Y")) children.add(c);
//                    }
//
//                    // 기본적으로 menu.getChildren 은 null 일 수 없기에, 접근권이 하나도 없는 경우 null 로 구분 짓습니다.
//                    if (children.isEmpty()) {
//                        m.addChildren(null);
//                    } else {
//                        m.addChildren(children);
//                    }
//                }
//
//                // 하위 메뉴는 있지만 접근권이 없어 하나도 열지 못한 경우(ex: 사용자 실수), 상위 메뉴도 열지 않습니다.
//                if (m.getChildren() != null) myMenus.add(m);
//            }
//        }
//
////        5) res
//        return myMenus.stream().map(Menu::toMenuDto).collect(Collectors.toList());
    }

    public void deleteMenuCache(String roleNm) {

    }
}
