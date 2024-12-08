package portfolio.eams.service.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.repo.system.MenuRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepo repo;


    @Override
    public List<MenuDto> selectMyMenu(Authentication auth) {
        return null;
    }

    @Override
    public void deleteMenuCache(String roleNm) {

    }
}
