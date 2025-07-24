package portfolio.eams.service.system;

import portfolio.eams.dto.system.UserDto;

import java.util.List;

public interface AdminUserService {
    /*
    User 엔티티는 공유하되 구분 편의상 관리자 인터페이스 분리
     */

    // 사용자 목록 조회

    /**
     * 사용자 목록 조회
     * @return 사용자 모든 정보
     */
    List<UserDto> selectList();

    /**
     * 사용자 정보 1건 조회
     * @param id user pk
     * @return 사용자 정보 1건
     */
    UserDto selectUserByAdmin(Long id);

    /**
     * 사용자 추가. erp 는 내부망 사용이라 외부 회원가입은 없음.
     * @param req
     */
    UserDto insertUser(UserDto.InsertReq req);

    /**
     * 비밀번호 제외 사용자 정보 변경
     * @return 변경 확인용 데이터
     */
    UserDto updateUser();


    /**
     * 퇴직 처리. 계정 비활성화 및 퇴직일 지정.
     * @param id user pk
     */
    void quitUser(Long id);


    /**
     * 사용자 삭제.
     * @param id user pk
     */
    UserDto deleteUser(Long id);


}
