package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.RoleAuth;

@Repository
public interface RoleAuthRepo extends JpaRepository<RoleAuth, Long> {
}
