package portfolio.eams.service.system;


import portfolio.eams.dto.system.AuthDto;

import java.util.List;

public interface AuthService {


    /**
     * 기초 메뉴 권한 생성
     * @param req 파라미터
     * @return dto
     */
    List<AuthDto> createAuth4Init(AuthDto.Req req);

}
