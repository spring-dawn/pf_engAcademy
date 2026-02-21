package portfolio.eams.service.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.UserRepo;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepo repo;

    /*
    사용자 관리 api, JPA 기본 기능을 디폴트 Service 로 선언.
    목록 조회는 검색을 겸하므로 최적화를 위해 별도 쿼리 작성.
     */

//    List<UserDto> getUserTest();

    // 개인정보 확인
//    UserDto selectUser(Long id);

    // 마이페이지 개인정보 수정
//    UserDto updateUser();

    // 비밀번호 변경
//    void updatePw(UserDto.UpdatePwReq req);




    // ============================================= 공용 =====================================

    /**
     * 로그인 실패 시 횟수 +1 씩 증가.0
     *
     * @param id 사용자 번호
     */
    @Transactional
    public void countLoginFailure(Long id){
        repo.findById(id).ifPresent(User::loginFailure);
    };


    /**
     * 로그인 실패 횟수 초기화
     *
     * @param id 사용자 번호
     */
    @Transactional
    public void initLoginFailure(Long id){
        repo.findById(id).ifPresent(User::initLoginFailCnt);
    };


}
