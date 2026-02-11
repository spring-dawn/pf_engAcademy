package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.CommonCode;

import java.util.Optional;


@Repository
public interface CommonCodeRepo extends JpaRepository<CommonCode, Long> {
    Optional<CommonCode> findById(Long id);

}
