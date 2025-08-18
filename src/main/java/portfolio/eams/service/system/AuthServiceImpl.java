package portfolio.eams.service.system;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.AuthDto;
import portfolio.eams.entity.system.Auth;
import portfolio.eams.entity.system.Menu;
import portfolio.eams.repo.system.AuthRepo;
import portfolio.eams.repo.system.MenuRepo;
import portfolio.eams.util.MessageUtil;
import portfolio.eams.util.enums.EntityNm;
import portfolio.eams.util.enums.InfoMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthRepo repo;
    private final MenuRepo menuRepo;
    private final MessageUtil msgUtil;

    // Character[] 은 stream 사용 불가, Arrays.stream() 으로 감싸야 함
    private static final Character[] AUTH_TYPE_ARR = {'C', 'R', 'U', 'D'};


}
