package portfolio.eams.config.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.entity.system.Role;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.MenuRoleAcsRepo;
import portfolio.eams.repo.system.RoleRepo;

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
    private final MenuRoleAcsRepo menuRoleAcsRepo;

    @Transactional(readOnly = true)
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        boolean isAllowed = false;

        // 요청 uri, 접근자 정보 확인
        String reqUri = object.getRequest().getRequestURI();
        Authentication auth = authentication.get();

        // 반환값 때문에 if 지옥...
        // 1) 로그인했는지?
        if (auth.getPrincipal().equals("anonymousUser")) {
            // TODO 1.5) 권한체크 필요 없는 화면이면 통과

            // 2) 계정 권한(Role) 확인
            String roleKey = auth.getAuthorities().iterator().next().toString();
            long roleId = Long.parseLong(roleKey);
            Role role = roleRepo.findById(roleId).orElse(null);

            // 3) 권한 목록에 해당 메뉴가 존재하면 조회가능.
            if (
                    role != null
                            && menuRoleAcsRepo.existsByRoleAndMenu(role, menuRepo.findByUrl(reqUri).orElse(null))
            )
                isAllowed = true;
        }

        return new AuthorizationDecision(isAllowed);
    }
}
