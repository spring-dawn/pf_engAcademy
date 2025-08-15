package portfolio.eams.controller.view.noMenu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoMenuController {

    @RequestMapping("/signin")
    public String signIn(){
        return "pages/signin";
    }

    // 샘플 or 404 페이지 등...

}
