package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.entity.system.MenuRoleAcs;
import portfolio.eams.entity.system.Role;

import java.util.List;

@Repository
public interface MenuRoleAcsRepo extends JpaRepository<MenuRoleAcs, Long> {

    /**
     * 메뉴 접근권 매핑이 이미 존재하는지 검사
     * @param role
     * @param menu
     * @return
     */
    boolean existsByRoleAndMenu(Role role, Menu menu);

    List<MenuRoleAcs> findByRole_Id(Long roleId);

    /**
     * jpa 자동 쿼리 쓰자니 메서드명이 어디까지 길어지는 거예요
     * @param roleId 사용자 직급 pk
     * @param readYn 'Y': 읽기가능 메뉴
     * @param menuUseYn 'Y': 활성화 된 메뉴
     * @return 조건을 만족하면서 parent == null 최상위 메뉴만 솎아내기. or 출력 시 children 중복 생김
     */
    List<MenuRoleAcs> findByRole_IdAndReadYnAndMenu_UseYnAndMenu_ParentIsNullOrderByMenu_SortSeqAsc(Long roleId, Character readYn, Character menuUseYn);

}
