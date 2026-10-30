package portfolio.eams.config.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.Role;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.MenuRoleAcsRepo;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.service.system.MenuService;
import portfolio.eams.util.enums.MenuType;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationChecker implements AuthorizationManager<RequestAuthorizationContext> {
    /*
    접근자의 각 메뉴 권한 검사. T면 접근, F면 404 오류 페이지 리다이렉트.
    이후 Security Config 에서 .access 필터링 적용
     */

    private final RoleRepo roleRepo;
    private final MenuRepo menuRepo;
    private final MenuService menuService;
    private final MenuRoleAcsRepo menuRoleAcsRepo;

    @Transactional(readOnly = true)
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        // 260902 메뉴 구조 변경에 따른 권한체크 방식 변경
        boolean isAllowed = false;

        // 1) 요청 uri, 접근자 정보 확인, 인증했는지?
        String reqUri = object.getRequest().getRequestURI();
        Authentication auth = authentication.get();

        if (!auth.getPrincipal().equals("anonymousUser")){
            // 2) 캐싱된 전체 메뉴 리스트 호출, 접근하려는 uri 와 비교.
            List<MenuDto> list = menuService.selectList();
            for(MenuDto menu : list){
                if (reqUri.equals(menu.getUrl())) {
                    String mType = menu.getMenuType();

                    // 3) default 는 로그인 했으면 통과
//                    if (mType.equals(MenuType.M4.getValue())) {
                    if (MenuType.M4.isEquals(mType)) {
                        isAllowed = true;
                        break;
                    }

                    // 4) 사용자 권한 조사. role 에 연계된 메뉴 목록 가져오기
                    String roleKey = auth.getAuthorities().iterator().next().toString();
                    long roleId = Long.parseLong(roleKey);
                    Role role = roleRepo.findById(roleId).orElse(null);
                    if (role == null) break;

                    // 5) menu, tab 은 각각 조회권한 확인. sub 는 상위 menu 조회권이 있으면 상속하여 통과
//                    if (mType.equals(MenuType.M3.getValue())) {
                    if (MenuType.M3.isEquals(mType)) {
                        // 상위 메뉴찾기
                        Menu parent = menuRepo.findById(menu.getParentId()).orElse(null);
                        if (parent == null) break;

                        // 있는지 확인하기
                        if (menuRoleAcsRepo.existsByRoleAndMenu(role, menuRepo.findByUrl(parent.getUrl()).orElse(null))) {
                            isAllowed = true;
                            break;
                        }
                    } else {    // menu, tab
                        if (menuRoleAcsRepo.existsByRoleAndMenu(role, menuRepo.findByUrl(reqUri).orElse(null))) {
                            isAllowed = true;
                            break;
                        }
                    }

                }
            }
        }

        log.info("asdf 페이지={}, 조회가능?={}", reqUri, isAllowed);
        return new AuthorizationDecision(isAllowed);
    }
}
