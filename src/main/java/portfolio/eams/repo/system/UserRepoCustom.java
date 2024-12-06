package portfolio.eams.repo.system;

import portfolio.eams.dto.system.UserDto;

import java.util.List;

public interface UserRepoCustom {
    /*
    사용자 관리 api
    QueryDsl, MyBatis 등의 외부 퍼시스턴스 툴을 사용하는 경우 여기에 정의합니다.
     */

    List<UserDto> selectList(UserDto.SearchReq req);

}
