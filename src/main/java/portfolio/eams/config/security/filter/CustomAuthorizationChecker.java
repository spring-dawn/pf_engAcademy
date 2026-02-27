package portfolio.eams.config.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.MenuRoleAcs;
import portfolio.eams.entity.system.Role;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.MenuRoleAcsRepo;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.service.system.MenuService;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationChecker implements AuthorizationManager<RequestAuthorizationContext> {
    /*
    접근자의 각 메뉴 권한 검사. T면 접근, F면 404 오류 페이지 리다이렉트 합니다.
    이후 Security Config 에서 .access 필터링 적용
     */

    private final RoleRepo roleRepo;
    private final MenuRoleAcsRepo menuRoleAcsRepo;

    @Transactional(readOnly = true)
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        boolean isAllowed = false;

        // 요청 uri, 접근자 정보 확인
        String reqUri = object.getRequest().getRequestURI();
        Authentication auth = authentication.get();

        // 접근자가 아예 인증(로그인)되지 않았으면 무조건 F, 인증했다면 Role 정보 확인
        if (!auth.getPrincipal().equals("anonymousUser")) {
            // 요청 uri 이 대시보드인 경우 기본적으로 조회 가능
            if (reqUri.equals("/dashboard") || reqUri.equals("/")) {
                isAllowed = true;
            } else {
                String roleKey = auth.getAuthorities().iterator().next().toString();
                long roleId = Long.parseLong(roleKey);
                Role role = roleRepo.findById(roleId).orElse(null);

                if (role != null) {
                    // roleId 를 통해 메뉴 읽기권한 유무 검사
                    for (MenuRoleAcs acs : menuRoleAcsRepo.findByRole_Id(roleId)) {
                        Menu menu = acs.getMenu();

                        if (menu.getUrl().equals(reqUri) && acs.getReadYn() == 'Y') {
                            isAllowed = true;
                            break;
                        }
                    }
                }
            }
        }

//        log.info("asdf 요청 uri ={}, isAllowed={}", reqUri, isAllowed);

        // 접근하려는 메뉴 uri 가 접근자의 Role 에 읽기권한이 부여돼있는지 확인하여 T/F 반환
        return new AuthorizationDecision(isAllowed);
    }


}
