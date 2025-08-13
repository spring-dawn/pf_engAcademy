package portfolio.eams.controller.api.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.service.system.MenuService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sys/menu", produces = "application/json")
public class MenuController {
    private final MenuService service;

    @GetMapping("/mine")
    public ResponseEntity<?> selectMyMenu() {
        List<MenuDto> res = service.selectMyMenu();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
