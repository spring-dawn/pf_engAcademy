package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.MenuRoleAccess;

@Repository
public interface MenuRoleAcsRepo extends JpaRepository<MenuRoleAccess, Long> {
}
