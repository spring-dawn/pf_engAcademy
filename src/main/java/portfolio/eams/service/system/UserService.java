package portfolio.eams.service.system;

import portfolio.eams.dto.system.UserDto;

import java.util.List;

public interface UserService {
    /*
    사용자 관리 api, JPA 기본 기능을 디폴트 Service 로 선언.
    목록 조회는 검색을 겸하므로 최적화를 위해 별도 쿼리 작성.
     */

    List<UserDto> getUserTest();

    // 개인정보 확인
    UserDto selectUser(Long id);

    // 마이페이지 개인정보 수정
    UserDto updateUser();

    // 비밀번호 변경
    void updatePw(UserDto.UpdatePwReq req);




    // ============================================= 공용 =====================================

    /**
     * 로그인 실패 시 횟수 +1 씩 증가.0
     *
     * @param id 사용자 번호
     */
    void countLoginFailure(Long id);


    /**
     * 로그인 실패 횟수 초기화
     *
     * @param id 사용자 번호
     */
    void initLoginFailure(Long id);


}
