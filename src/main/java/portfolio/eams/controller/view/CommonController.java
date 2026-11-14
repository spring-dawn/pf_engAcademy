package portfolio.eams.controller.view;

import jakarta.servlet.http.HttpServletRequest;
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
   메뉴 등 공통 데이터 전송 처리
    */
    private final MenuService menuService;


    /**
     * 로그인 후 전역으로 사용할 세션별 권한메뉴목록 조회
     * @param model 데이터 매핑 객체
     */
    @ModelAttribute
    public void menus(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String roleKey = authentication.getAuthorities().iterator().next().toString();
        Long roleId = Long.parseLong(authentication.getAuthorities().toArray()[0].toString());

        List<MenuDto> list = menuService.selectMyMenu(roleId);
        model.addAttribute("menus", list);
    }


    /**
     * Thymeleaf 3.x 버전 이후로 템플릿에서 requestUri 를 지원하지 않으므로 전역 컨트롤러로 현재 접속 정보 전달
     * @param model 데이터 매핑 객체
     * @param request 현재 요청
     */
    @ModelAttribute
    public void pageData(Model model, HttpServletRequest request) {
        // 1) uri
        String pageUri = request.getRequestURI();

        // 2) 캐싱된 권한메뉴목록 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long roleId = Long.parseLong(authentication.getAuthorities().toArray()[0].toString());
        List<MenuDto> list = menuService.selectMyMenu(roleId);

        // 3) 탐색, 현 메뉴 정보 가져오기
        List<MenuDto> pageList = menuService.getPageNav(list, pageUri);

        // return
        model.addAttribute("pageUri", pageUri);
        model.addAttribute("pageList", pageList);
    }


    /**
     * 페이지별 매핑된 공통코드 호출
     * @param model 데이터 매핑 객체
     * @param request 현재 요청
     */
    @ModelAttribute
    public void commonCodes(Model model, HttpServletRequest request) {
        // TODO:
    }


}
