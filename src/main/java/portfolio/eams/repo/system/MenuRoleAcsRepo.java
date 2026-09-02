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
     * 계정 권한에 메뉴 조회권이 있는지 검사
     * @param role 사용자 권한
     * @param menu 접근하려는 메뉴
     * @return T: 존재, 조회권 있음. F: 접근불가.
     */
    boolean existsByRoleAndMenu(Role role, Menu menu);

    List<MenuRoleAcs> findByRole_Id(Long roleId);

    /**
     * 사용자 계정 권한의 접근 가능 메뉴목록 얻기.
     * @param roleId 권한 pk
     * @param useYn 메뉴 사용여부 'Y'
     * @return 사용자 접근 가능 메뉴목록 root dto
     */
    List<MenuRoleAcs> findByRole_IdAndMenu_ParentIsNullAndMenu_UseYn(Long roleId, Character useYn);




//    List<MenuRoleAcs> findByRole_IdAndReadYnAndMenu_UseYnAndMenu_ParentIsNullOrderByMenu_SortSeqAsc(Long roleId, Character readYn, Character menuUseYn);
//    List<MenuRoleAcs> findByRole_idAndMenu_UseYnAndMenu_ParentIsNullAndMenu_MenuType(Long roleId, Character useYn, String menuType);
}
