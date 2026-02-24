package portfolio.eams.service.system;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.MenuRoleAcs;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.MenuRoleAcsRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepo repo;
    private final MenuRoleAcsRepo acsRepo;


    /**
     * 사용자 역할 범위에 따른 메뉴 조회. 권한별 캐싱 적용하여 최적화.
     * @param roleId 인증 정보에서 추출한 role pk, 첫 번째 파라미터(#p0)를 캐싱 key 로 사용.
     * @return 사용자 권한별 메뉴 목록 dto -> 화면 렌더링
     */
//    @Cacheable(value = "mymenu", key = "#roleId") // 이름 인식 불명 오류로 #p0 사용
    @Cacheable(value = "mymenu", key = "#p0")
    public List<MenuDto> selectMyMenu(Long roleId){
        // 인증 정보를 열어 roleKey 를 찾아오는 건 컨트롤러에 위임, 여기선 서비스 로직만 작성
        List<MenuDto> result = acsRepo.findByRole_IdAndReadYnAndMenu_UseYnAndMenu_ParentIsNullOrderByMenu_SortSeqAsc(roleId, 'Y', 'Y')
                .stream()
                .map(MenuRoleAcs::getMenu)
                .map(Menu::toRes)
                .toList();

        return result;
    }


    /**
     * 메뉴 단일건 조회
     *
     * @param url 메뉴 url
     * @return dto
     */
//   public MenuDto selectMenu(String url);


    /**
     * 권한 내역 변경 시 해당 역할의 캐시 삭제
     *
     * @param roleId 인증 정보에서 추출한 role pk. 메뉴 데이터 캐싱 시 key 로 사용. @Cacheable 과 동일
     */

    @CacheEvict(value = "mymenu", key = "#p0")
    public void deleteMenuCacheByRoleNm(Long roleId){
        log.info(roleId+"번 직급의 메뉴 접근권 내역이 변경됩니다.");
    };


    /**
     * @param rootMenus 접근권 있는 최상위 메뉴들
     * @param allowed   접근권 있는 메뉴 전체의 id
     * @return 필터링 메뉴
     */
//    public List<Menu> filterAllowedMenu(List<Menu> rootMenus, Set<Long> allowed);


}
