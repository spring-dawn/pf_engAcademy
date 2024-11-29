package portfolio.eams.service.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.repo.system.UserRepo;

@Slf4j
@Service
@RequiredArgsConstructor // repo import
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
    // 리포지토리 임포트. @Autowired 는 테스트 클래스에서 사용.
    private final UserRepo repo;


    /**
     * 스프링 시큐리티 연계 사용자 인증
     *
     * @param username the username identifying the user whose data is required.
     * @return 사용자 아이디, 권한 목록 등
     * @throws UsernameNotFoundException 사용자 조회 불가
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    // 사용자 관리
    @Override
    public UserDto insertUser() {
        return null;
    }

    @Override
    public UserDto updateUser() {
        return null;
    }

    @Override
    public UserDto updateUserByAdmin() {
        return null;
    }

    @Override
    public UserDto quitUser(Long id) {
        return null;
    }

    @Override
    public void countLoginFailure(Long id) {
        // TODO document why this method is empty
    }

    @Override
    public void initLoginFailure(Long id) {
        // TODO document why this method is empty
    }

    @Override
    public UserDto deleteUser() {
        return null;
    }

}
