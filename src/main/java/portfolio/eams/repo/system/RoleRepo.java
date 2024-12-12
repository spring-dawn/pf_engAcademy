package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
