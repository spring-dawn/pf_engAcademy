package portfolio.eams.controller.api.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.service.system.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/system/user", produces = "application/json")
public class UserController {
    /*
    사용자 api 컨트롤러
     */
    private final UserService service;

    @GetMapping("/users")
    public ResponseEntity<?> insertUser() {
        UserDto res = service.insertUser();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
