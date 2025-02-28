package portfolio.eams.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    /*
    뷰(화면) 컨트롤러
    경로 prefix 는 application.yml 에서 디폴트 적용. 현재 : /resource/templates/
    suffix : .html
     */

    @RequestMapping("/signin")
    public String signIn(){
        return "pages/signin";
    }


    @RequestMapping("/signup")
    public String signUp(){
        return "pages/signup";
    }


    @RequestMapping("/dashboard")
    public String dashboard(){
        return "pages/dashboard";
    }

}
