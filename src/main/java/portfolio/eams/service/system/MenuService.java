package portfolio.eams.service.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.MenuRoleAcsRepo;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepo repo;
    private final MenuRoleAcsRepo roleRepo;


    /**
     * 사용자 역할 범위에 따른 메뉴 조회. 권한별 캐싱 적용하여 최적화.
     * @param roleKey 인증 정보에서 추출한 role pk. 메뉴 데이터 캐싱 시 key 로 사용.
     * @return 사용자 권한별 메뉴 목록 dto -> 화면 렌더링
     */
    List<MenuDto> selectMyMenu(String roleKey){
        return null;
    }


    /**
     * 메뉴 단일건 조회
     *
     * @param url 메뉴 url
     * @return dto
     */
//    MenuDto selectMenu(String url);


    /**
     * 권한 내역 변경 시 해당 역할의 캐시 삭제
     *
     * @param roleKey 인증 정보에서 추출한 role pk. 메뉴 데이터 캐싱 시 key 로 사용.
     */
//    void deleteMenuCacheByRoleNm(String roleKey);


    /**
     * @param rootMenus 접근권 있는 최상위 메뉴들
     * @param allowed   접근권 있는 메뉴 전체의 id
     * @return 필터링 메뉴
     */
//    List<Menu> filterAllowedMenu(List<Menu> rootMenus, Set<Long> allowed);


}
