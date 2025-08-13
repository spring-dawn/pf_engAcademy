package portfolio.eams.controller.api.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.User;
import portfolio.eams.repo.mybatis.UserMapper;
import portfolio.eams.service.system.AdminUserService;
import portfolio.eams.service.system.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sys/user", produces = "application/json")
public class UserController {
    /*
    사용자 api 컨트롤러
     */
    private final UserService service;


//    @GetMapping("/users")
//    public ResponseEntity<?> insertUser() {
//        UserDto res = service.insertUser();
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/users")
//    public ResponseEntity<?> deleteUser(@RequestBody UserDto.DeleteReq req) {
//        return new ResponseEntity<>(service.deleteUser(req), HttpStatus.OK);
//    }



}
