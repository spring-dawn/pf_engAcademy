package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, UserRepoCustom {
    // Jpa 디폴트 리포지토리 외에 QueryDsl 리포지토리 역시 상속하여 단일하게 사용.

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

}
