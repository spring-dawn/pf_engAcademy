package portfolio.eams.service.system;

import org.springframework.security.core.Authentication;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Menu;

import java.util.List;
import java.util.Set;

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
    void deleteMenuCacheByRoleNm(String roleNm);


    /**
     * @param rootMenus 접근권 있는 최상위 메뉴들
     * @param allowed   접근권 있는 메뉴 전체의 id
     * @return 필터링 메뉴
     */
    List<Menu> filterAllowedMenu(List<Menu> rootMenus, Set<Long> allowed);


    /**
     * 메뉴 생성
     * @param req 파라미터
     * @return 메뉴 dto. 커밋 전까지 id 없음에 주의.
     */
    MenuDto createMenu(MenuDto.Req req);


}
