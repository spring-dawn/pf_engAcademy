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

}
