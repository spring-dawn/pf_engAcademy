package portfolio.eams.service.system;

import org.springframework.security.core.Authentication;
import portfolio.eams.dto.system.MenuDto;

import java.util.List;

public interface MenuService {
    /**
     * 사용자 역할 범위에 따른 메뉴 조회. 권한별 캐싱 적용하여 최적화.
     * @param auth from SecurityContext
     * @return 메뉴 목록 dto -> 화면 렌더링
     */
    List<MenuDto> selectMyMenu(Authentication auth);


    /**
     * 권한 내역 변경 시 해당 역할의 캐시 삭제
     * @param roleNm 역할명(key)
     */
    void deleteMenuCache(String roleNm);

}
