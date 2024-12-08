package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Menu;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
}
