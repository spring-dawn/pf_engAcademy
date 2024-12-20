package portfolio.eams.service.system;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import portfolio.eams.dto.system.RoleDto;
import portfolio.eams.entity.system.Auth;
import portfolio.eams.entity.system.Role;
import portfolio.eams.entity.system.RoleAuth;
import portfolio.eams.repo.system.AuthRepo;
import portfolio.eams.repo.system.RoleAuthRepo;
import portfolio.eams.repo.system.RoleRepo;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepo repo;
    private final AuthRepo authRepo;
    private final RoleAuthRepo roleAuthRepo;


    @Transactional
    public RoleDto createRole(RoleDto.Req req) {
//        1) validation
        if (!StringUtils.hasText(req.getRoleNm())) throw new IllegalArgumentException(InfoMsg.NPE.getMsg());
        if (repo.existsByRoleNm(req.getRoleNm()))
            throw new EntityExistsException(InfoMsg.ALREADY_EXISTS.format(EntityNm.ROLE));

//        2) create and save role
        Role role = Role.builder()
                .roleNm(req.getRoleNm())
                .desc(req.getDesc())
                .order(req.getOrder())
                .useYn('Y') // ?? DynamicInsert 를 쓰고 있는데 왜 안 먹히지
                .build();
        repo.save(role);

//        3) find authList
        List<Auth> authList = authRepo.findAllById(req.getAuthIdList());

//        4) add authList to role
        List<RoleAuth> roleAuthList = authList.stream()
                .map(auth -> RoleAuth.builder()
                        .role(role)
                        .auth(auth)
                        .build())
                .toList();
        roleAuthRepo.saveAll(roleAuthList);
        role.addAuthList(roleAuthList);

        return role.toRes();
    }


    @Override
    public RoleDto updateRole(RoleDto.Req req) {
        return null;
    }

}
