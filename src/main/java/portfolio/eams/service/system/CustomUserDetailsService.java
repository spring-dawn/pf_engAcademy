package portfolio.eams.service.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MessageUtil;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;


@Slf4j
@Service
@RequiredArgsConstructor // repo import
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo repo;
    private final MessageUtil msgUtil;

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
//                .orElseThrow(() -> new UsernameNotFoundException(InfoMsg.ENTITY_NOT_FOUND.format(EntityNm.USER)));
                .orElseThrow(() -> new UsernameNotFoundException(msgUtil.get("ent.not.found", EntityNm.USER)));
        user.getAuthorities();

        log.info("asdf 로그인 정보 조회: "+user.getUserId());
        return user;
    }

}
