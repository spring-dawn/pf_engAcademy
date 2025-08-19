package portfolio.eams.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.service.system.MenuService;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final MenuService menuService;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        // TODO 요청한 페이지에 대한 사용자의 조회권한이 있는지 확인하여 T:F 반환
        boolean isAllowed = false;
        String reqUri = object.getRequest().getRequestURI();

        Authentication auth = authentication.get();

        // 인증 정보가 존재하는 경우에만
        if (!auth.getPrincipal().equals("anonymousUser")) {
            String roleKey = auth.getAuthorities().iterator().next().toString();
            List<MenuDto> menuList = menuService.selectMyMenu(roleKey);

            if (reqUri.equals("/") || reqUri.equals("/dashboard")) {
                isAllowed = true;
            } else {
                // reqUri 가 권한 목록에 있으면 true, 없으면 false.
                isAllowed = existsPermittedUrl(menuList, reqUri);
            }
        }

        return new AuthorizationDecision(isAllowed);
    }


    // 재귀
    public boolean existsPermittedUrl(List<MenuDto> menuList, String reqUri) {
        boolean isPermitted = false;

        for (MenuDto menu : menuList) {
            String url = menu.getUrl();

            if (url.equals(reqUri)) {
                isPermitted = true;
                break;
            } else {
                if (reqUri.startsWith(url) && !menu.getChildren().isEmpty()) {
                    isPermitted = existsPermittedUrl(menu.getChildren(), reqUri);
                    break;
                }
            }
        }

        return isPermitted;
    }

}
