package portfolio.eams.repo.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.eams.entity.system.Menu;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {

    /**
     * url 로 메뉴 조회
     * @param url url(unique)
     * @return 해당 메뉴 옵셔널
     */
    Optional<Menu> findByUrl(String url);


    /**
     * 상위 참조가 없는 메뉴 목록 조회
     * @return 최상위 메뉴 목록
     */
    List<Menu> findByParentIsNull();


    /**
     * url 중복 확인
     * @param url url e.g.) /system/user...
     * @return T: 이미 존재하여 중복, F: 중복 없음
     */
    boolean existsByUrl(String url);

}
