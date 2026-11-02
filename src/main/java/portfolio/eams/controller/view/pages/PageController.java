package portfolio.eams.controller.view.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    /*
    뷰(화면) 매핑 컨트롤러
    경로 prefix 는 application.yml 에서 디폴트 적용. 현재 : /resource/templates/
    suffix : .html

    메뉴 데이터를 사용하는 페이지만 명시.
     */


    // 대시보드
    @RequestMapping(value = {"/", "/dashboard"})
    public String dashboard(){
        return "pages/dashboard";
    }

    @RequestMapping("/myPage")
    public String myPage(){
        return "pages/myPage";
    }

    @RequestMapping("/changePw")
    public String changePw(){
        return "pages/changePw";
    }

    // ===================== 시스템 관리 ============================
    // 사용자
    @RequestMapping("/system/user")
    public String user(){
        return "pages/system/user";
    }

    // 권한
    @RequestMapping("/system/role")
    public String role(){
        return "pages/system/role";
    }

    // 공통코드_목록(탭)
    @RequestMapping(value = {"/system/commonCode", "/system/commonCode/list"})
    public String codeList(){
        return "pages/system/commonCode/list";
    }

    // 공통코드_사용처
    @RequestMapping("/system/commonCode/usage")
    public String codeUsage(){
        return "pages/system/commonCode/usage";
    }

    // ===================== 학원: 학생 ============================
    // 학생목록
    @RequestMapping("/student/list")
    public String studentList(){
        return "pages/student/list";
    }

    // 학생목록 서브
    @RequestMapping("/student/list/read")
    public String studentListRead(){
        return "pages/student/list/read";
    }
    @RequestMapping("/student/list/write")
    public String studentListWrite(){
        return "pages/student/list/write";
    }

    // 성적관리
    @RequestMapping("/student/score")
    public String score(){
        return "pages/student/list";
    }

    // 학생 상담내역
    @RequestMapping("/student/counsel")
    public String counsel(){
        return "pages/student/counsel";
    }

    // ===================== 학원: 학급 ============================
    // 목록
    @RequestMapping("/classroom/list")
    public String classroom(){
        return "pages/classroom/list";
    }

    // 학급별 수업배치
    @RequestMapping("/classroom/course")
    public String course(){
        return "pages/classroom/course";
    }

    // 학급별 시간표관리
    @RequestMapping("/classroom/timetable")
    public String timetable(){
        return "pages/classroom/timetable";
    }

    @RequestMapping("/classroom/timetable/read")
    public String timetableRead(){
        return "pages/classroom/timetable/read";
    }

    @RequestMapping("/classroom/timetable/write")
    public String timetableWrite(){
        return "pages/classroom/timetable/write";
    }


    // ===================== 행정 ============================

    // 공지사항 TODO: sub 화면 2개 더 만들려고 했지만 좀 과한 것 같기도?
    @RequestMapping("/operation/notice")
    public String notice(){
        return "pages/operation/notice";
    }

    // 회의록
    @RequestMapping("/operation/meetingLog")
    public String meetingLog(){
        return "pages/operation/meetingLog";
    }


    // ===================== 기타 ============================
    
    // 문자발송
    @RequestMapping("/sms")
    public String sms(){
        return "pages/sms";
    }

    // 통계시각화
    @RequestMapping("/statics")
    public String statics(){
        return "pages/statics";
    }
}
