package portfolio.eams.service.system;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.dto.system.CodeDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeServiceImpl implements CodeService {

    @Override
    public CodeDto insertCode(CodeDto.Req req) {
        return null;
    }
}
