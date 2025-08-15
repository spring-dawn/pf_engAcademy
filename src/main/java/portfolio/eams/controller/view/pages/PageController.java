package portfolio.eams.controller.view.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    /*
    뷰(화면) 컨트롤러
    경로 prefix 는 application.yml 에서 디폴트 적용. 현재 : /resource/templates/
    suffix : .html

    메뉴 데이터를 사용하는 페이지만 명시.
     */

    @RequestMapping(value = {"/", "/dashboard"})
    public String dashboard(){
        return "pages/dashboard";
    }

}
