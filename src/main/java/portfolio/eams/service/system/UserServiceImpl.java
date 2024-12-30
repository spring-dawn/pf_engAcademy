package portfolio.eams.service.system;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MyUtil;
import portfolio.eams.util.SHA256Util;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor // repo import
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
    // 리포지토리 임포트. @Autowired 는 테스트 클래스에서 사용.
    private final UserRepo repo;
    private final RoleRepo roleRepo;


    /**
     * 스프링 시큐리티 연계 사용자 인증
     *
     * @param userId (default: username) identifying the user whose data is required.
     * @return 사용자 아이디, 권한 목록 등
     * @throws UsernameNotFoundException 사용자 조회 불가
     */
    @Override
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 계정 정보 + 권한
        User user = repo.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.USER)));
        user.getAuthorities();

        return user;
    }


    // 사용자 관리
    @Override
    public UserDto insertUser() {
        return null;
    }

    @Transactional
    public UserDto createUser4Init(String userId, Character admYn, String roleNm) {
//        1) validation
        User isExist = repo.findByUserId(userId).orElse(null);
        if (isExist != null) return isExist.toRes();
        if (!StringUtils.hasText(userId))
            throw new IllegalArgumentException(InfoMsg.NPE.getMsg());

//        2) pw encryption
        String salt = "";
        String salted = "";
        try {
            SHA256Util.PwDto pwDto = SHA256Util.createPw(userId);

            salt = pwDto.salt();
            salted = pwDto.salted();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

//        3) create user
        Role role = roleRepo.findByRoleNm(roleNm)
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.ROLE)));

        return repo.save(
                        User.builder()
                                .userId(userId)
                                .salt(salt)
                                .userPw(salted)
                                .userNm(userId)
                                .admYn(admYn)
                                .tel("010-1111-1111")
                                .email(userId + "@test.com")
                                .joinYmd(LocalDate.now())
                                .role(role)
                                .build())
                .toRes();
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


    @Transactional
    public void countLoginFailure(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.USER)));

        user.updateLoginFailCnt();
    }

    @Override
    public void initLoginFailure(Long id) {
//        1) find target
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.USER)));

        user.initLoginFailCnt();
    }

    @Transactional
    public UserDto deleteUser(UserDto.DeleteReq req) {
        //TODO: check session

//        1) find target
        User user = repo.findById(req.getId())
                .orElseThrow(() -> new EntityNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.USER)));

//        2) check auth


//        ) delete
        repo.delete(user);
        return user.toRes();

    }

}
