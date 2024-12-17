package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Auth;

@Repository
public interface AuthRepo extends JpaRepository<Auth, Long> {
}
