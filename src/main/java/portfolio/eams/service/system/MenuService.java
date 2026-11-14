package portfolio.eams.service.system;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.MenuRoleAcs;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.repo.system.MenuRoleAcsRepo;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;
import portfolio.eams.util.enums.MenuType;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepo repo;
    private final MenuRoleAcsRepo acsRepo;

    /**
     * 전체 메뉴 리스트 캐싱데이터.
     * @return
     */
    @Cacheable(value = "menuList")
    public List<MenuDto> selectList(){
        return repo.findAll().stream().map(Menu::toRes).toList();
    }


    /**
     * 사용자 역할 범위에 따른 메뉴 조회. 권한별 캐싱 적용하여 최적화.
     * @param roleId 인증 정보에서 추출한 role pk, 첫 번째 파라미터(#p0)를 캐싱 key 로 사용.
     * @return 사용자 권한별 메뉴 목록 dto -> 화면 렌더링
     */
//    @Cacheable(value = "mymenu", key = "#roleId") // 이름 인식 불명 오류로 #p0 사용
    @Cacheable(value = "mymenu", key = "#p0")
    public List<MenuDto> selectMyMenu(Long roleId){
        // 인증 정보를 열어 roleKey 를 찾아오는 건 컨트롤러에 위임, 여기선 서비스 로직만 작성
        List<MenuDto> result = acsRepo.findByRole_IdAndMenu_ParentIsNullAndMenu_UseYn(roleId, 'Y')
                .stream()
                .map(MenuRoleAcs::getMenu)
                .map(Menu::toRes)
                .toList();

        return result;
    }


    /**
     * 메뉴 단일건 조회
     * @param url 메뉴 url(uri)
     * @return dto
     */
   public MenuDto selectMenu(String url){
       Menu menu = repo.findByUrl(url)
               .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.MENU)));
       return menu.toRes();
   };


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


    /**
     * 캐싱된 트리구조 리스트를 재귀적으로 솎아 각 화면(page) 상단에 출력할 메뉴정보 탐색
     * @param cacheTree 캐싱된 권한메뉴목록
     * @param pageUri 현재 접속 화면 메뉴정보목록 or 탭 목록
     */
    public List<MenuDto> getPageNav(List<MenuDto> cacheTree, String pageUri) {
        // 1)
        if (cacheTree.isEmpty()) return Collections.emptyList();

        // 2) 인자로 받은 cacheTree 깊이에서 uri 일치여부 평면 탐색
        for (MenuDto dto : cacheTree) {
            if (dto.getUrl().equals(pageUri)) {
                // 메뉴타입 검사: TAB 이면 siblings 전부 반환
                if (MenuType.M2.isEquals(dto.getMenuType())) {
                    return cacheTree;
                } else {
                    // default, sub, bar 타입이며 일치하는 단일값 즉각 반환
                    return List.of(dto);
                }

            // 3) 해당 깊이에서 못 찾은 경우, uri 를 포함하는 하위노드로 진입
            } else {
                if (pageUri.startsWith(dto.getUrl()) && !dto.getChildren().isEmpty()) {
//                    log.info("asdf 하위노드로 재귀탐색. 현재 깊이={}, 탐색url={}", dto.getDepth(), dto.getUrl());
                    List<MenuDto> result = getPageNav(dto.getChildren(), pageUri);
                    if(!result.isEmpty()) return result;
                }
            }
        }

        // 4) 구색맞추기용 return
        return Collections.emptyList();
    }

}
