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

    // 시스템관리
    @RequestMapping("/system/user")
    public String user(){
        return "pages/system/user";
    }

    @RequestMapping("/system/commonCode")
    public String code(){
        return "pages/system/commonCode";
    }

    @RequestMapping("/system/role")
    public String role(){
        return "pages/system/role";
    }

    //
    @RequestMapping("/academy/student")
    public String student(){
        return "pages/academy/student";
    }

    @RequestMapping("/academy/classroom")
    public String classroom(){
        return "pages/academy/classroom";
    }

    @RequestMapping("/academy/score")
    public String score(){
        return "pages/academy/score";
    }

    @RequestMapping("/academy/timetable")
    public String timetable(){
        return "pages/academy/timetable";
    }

    //
    @RequestMapping("/operation/notice")
    public String notice(){
        return "pages/operation/notice";
    }

    @RequestMapping("/operation/meetingLog")
    public String meetingLog(){
        return "pages/operation/meetingLog";
    }

    @RequestMapping("/operation/sms")
    public String sms(){
        return "pages/operation/sms";
    }

    //
    @RequestMapping("/statics")
    public String statics(){
        return "pages/statics";
    }
}
