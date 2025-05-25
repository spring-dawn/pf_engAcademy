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

    @Override
    public RoleDto updateRole(RoleDto.Req req) {
        return null;
    }

}
