package portfolio.eams.service.system;

import portfolio.eams.dto.system.UserDto;

import java.util.List;

public interface UserService {
    /*
    사용자 관리 api 정의.
    목록 조회는 검색을 겸하므로 최적화를 위해 별도 쿼리 작성.
     */

    public UserDto insertUser();

    // 사용자 정보 수정
    public UserDto updateUserByAdmin();

    public UserDto updateUser();

    // 사용자 삭제
    public UserDto deleteUser();

    // 사용자 퇴사
    public UserDto quitUser(Long id);

    // 로그인 실패 횟수 증가
    public void countLoginFailure(Long id);

    // 로그인 실패 횟수 초기화
    public void initLoginFailure(Long id);


}
