package portfolio.eams.controller.api.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.service.system.AdminUserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sys/user/admin", produces = "application/json")
public class AdminUserController {
    private final AdminUserService service;

    @PostMapping("/signup")
    public ResponseEntity<?> insertUser(@RequestBody UserDto.InsertReq req) {
        UserDto res = service.insertUser(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody UserDto.UpdateReq req) {
        UserDto res = service.updateUser(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/quit")
    public void quitUser(@RequestBody UserDto.QuitReq req) {
        service.quitUser(req);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable("id") Long id) {
        UserDto res = service.deleteUser(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
