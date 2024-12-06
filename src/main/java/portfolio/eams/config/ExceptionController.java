package portfolio.eams.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import portfolio.eams.dto.ResponseDto;
import portfolio.eams.util.MyUtil;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    /*
    전역 예외 처리 포맷
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultServerException(Exception e) {
        log.error("Exception!", e);
        ResponseDto defaultRes = new ResponseDto("500", "내부 에러 발생", MyUtil.timestamp());

        return new ResponseEntity<>(
                defaultRes,
                HttpStatus.INTERNAL_SERVER_ERROR  // 500
        );
    }

}
