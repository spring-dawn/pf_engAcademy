package portfolio.eams.service.system;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.repo.system.UserRepo;
import portfolio.eams.util.MessageUtil;
import portfolio.eams.util.SHA256Util;
import portfolio.eams.util.enums.EntityNm;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final MessageUtil msgUtil;
    private final UserRepo repo;
    private final RoleRepo roleRepo;


    public List<UserDto> selectList() {
        return List.of();
    }

    public UserDto selectUserByAdmin(Long id) {
        return null;
    }

    @Transactional
    public UserDto insertUser(UserDto.InsertReq req) {
        // validation: id, email
        if(repo.existsByUserId(req.getUserId())) throw new EntityExistsException("아이디중복");
        if(repo.existsByEmail(req.getEmail())) throw new EntityExistsException("이메일중복");

        // chosen role
        Role role = roleRepo.findById(req.getRoleId())
                .orElseThrow(EntityNotFoundException::new);

        // pw encrypt
        if(!hasText(req.getUserPw())) throw new IllegalArgumentException("비밀번호 누락");

        User user = null;
        try {
            SHA256Util.PwDto pwDto = SHA256Util.createPw(req.getUserPw());

            user = User.builder()
                    .userId(req.getUserId())
                    .userNm(req.getUserNm())
                    .userPw(pwDto.salted())
                    .salt(pwDto.salt())
                    .email(req.getEmail())
                    .tel(req.getTel())
                    .joinYmd(LocalDate.parse(req.getJoinYmd()))
                    .pwModYmd(LocalDate.now())
                    .role(role)
                    .build();
        } catch (Exception e) {
            log.error("Error: 비밀번호 암호화 시 오류 발생");
        }

        repo.save(user);
        return user.toRes();
    }

    @Transactional
    public UserDto updateUser(UserDto.UpdateReq req) {
        // find target
        User user = repo.findById(req.getId())
                .orElseThrow(() -> new EntityNotFoundException(msgUtil.get("ent.not.found", EntityNm.USER)));

        // 역할 변경이 있는 경우
        if(user.getRole().getId() != req.getRoleId()){
            Role role = roleRepo.findById(req.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException(msgUtil.get("ent.not.found", EntityNm.ROLE)));
            user.updateUserRole(role);
        }

        user.updateUser(req);
        return user.toRes();
    }

    @Transactional
    public void quitUser(UserDto.QuitReq req) {
        User user = repo.findById(req.getId())
                .orElseThrow(() -> new EntityNotFoundException(msgUtil.get("ent.not.found", EntityNm.USER)));

        user.quitUser(req);
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
