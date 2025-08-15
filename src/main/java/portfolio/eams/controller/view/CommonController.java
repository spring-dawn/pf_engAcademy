package portfolio.eams.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.service.system.MenuService;

import java.util.List;

@Slf4j
@ControllerAdvice(basePackages = "portfolio.eams.controller.view.pages")
@RequiredArgsConstructor
public class CommonController {

    /*
   전역 컨트롤러
    */
    private final MenuService menuService;

    @ModelAttribute
    public void menus(Model model) {
//        세션이 생기고 유지되는 동안에만 적용되도록 조절해야 합니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleKey = authentication.getAuthorities().toArray()[0].toString();

        List<MenuDto> list = menuService.selectMyMenu(roleKey);
        model.addAttribute("menus", list);
    }
}
