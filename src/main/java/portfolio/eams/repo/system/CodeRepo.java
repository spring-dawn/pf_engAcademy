package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Code;

import java.util.Optional;


@Repository
public interface CodeRepo extends JpaRepository<Code, Long> {
    Optional<Code> findById(Long id);

}
