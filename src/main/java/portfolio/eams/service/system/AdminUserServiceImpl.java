package portfolio.eams.service.system;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MessageUtil;
import portfolio.eams.util.enums.EntityNm;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepo repo;
    private final MessageUtil msgUtil;


    public List<UserDto> selectList() {
        return List.of();
    }

    public UserDto selectUserByAdmin(Long id) {
        return null;
    }

    public UserDto insertUser(UserDto.InsertReq req) {
        return null;
    }

    public UserDto updateUser() {
        return null;
    }

    public void quitUser(Long id) {

    }

    @Transactional
    public UserDto deleteUser(Long id) {
        // find target
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(msgUtil.get("ent.not.found", EntityNm.USER)));

        // limit
        if(user.getAdmYn().equals('Y'))
            throw new RuntimeException(msgUtil.get("ent.limit.del", EntityNm.USER));

        // delete and res
        repo.delete(user);
        return user.toRes();
    }
}
