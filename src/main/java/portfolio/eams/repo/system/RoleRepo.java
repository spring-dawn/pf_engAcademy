package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    /**
     * 역할 중복 검사
     * @param roleNm 직책명 uk
     * @return T: 존재 , F: 없음
     */
    boolean existsByRoleNm(String roleNm);


    /**
     * 역할명으로 역할 엔티티 조회
     * @param roleNm
     * @return
     */
    Optional<Role> findByRoleNm(String roleNm);

}
