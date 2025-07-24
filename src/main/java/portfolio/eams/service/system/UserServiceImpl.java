package portfolio.eams.service.system;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.mybatis.UserMapper;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MessageUtil;
import portfolio.eams.util.SHA256Util;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor // repo import
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    // 리포지토리 임포트. @Autowired 는 테스트 클래스에서 사용.
    private final UserRepo repo;
    private final UserMapper mapper;
    private final MessageUtil msgUtil;

    @Override
    public List<UserDto> getUserTest() {
        return mapper.getUserList();
    }

    @Override
    public UserDto selectUser(Long id) {
        return null;
    }

    @Override
    public UserDto updateUser() {
        return null;
    }


    @Override
    public void updatePw(UserDto.UpdatePwReq req) {

    }


    // ===============================================================================================

    @Transactional
    public void countLoginFailure(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(msgUtil.get("ent.not.found", EntityNm.USER)));

        user.updateLoginFailCnt();
    }

    @Override
    public void initLoginFailure(Long id) {
//        1) find target
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(msgUtil.get("ent.not.found", EntityNm.USER)));

        user.initLoginFailCnt();
    }


}
