package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.AccessLog;

@Repository
public interface AccessLogRepo extends JpaRepository<AccessLog, Long> {
}
