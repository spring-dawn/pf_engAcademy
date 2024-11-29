package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.User;

@Repository
public interface UserRepo extends JpaRepository<Long, User> {
}
