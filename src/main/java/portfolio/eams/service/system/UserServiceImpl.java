package portfolio.eams.service.system;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MyUtil;
import portfolio.eams.util.enums.InfoMsg;

@Slf4j
@Service
@RequiredArgsConstructor // repo import
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final String THIS = "사용자";

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
//        1) find target
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MyUtil.getEnum(InfoMsg.ENTITY_NOT_FOUND, THIS)));

    }

    @Transactional
    public UserDto deleteUser(UserDto.DeleteReq req) {
        //TODO: check session

//        1) find target
        User user = repo.findById(req.getId())
                .orElseThrow(() -> new EntityNotFoundException(MyUtil.getEnum(InfoMsg.ENTITY_NOT_FOUND, THIS)));

//        2) check auth


//        ) delete
        repo.delete(user);
        return user.toRes();

    }

}
