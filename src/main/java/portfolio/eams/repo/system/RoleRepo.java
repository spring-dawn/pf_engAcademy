package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    /**
     * 역할명으로 역할 엔티티 조회
     * @param roleNm
     * @return
     */
    Optional<Role> findByRoleNm(String roleNm);
}
