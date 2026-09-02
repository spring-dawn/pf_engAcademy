package portfolio.eams.service.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import portfolio.eams.dto.system.MenuDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    MenuService service;

    @Test
    @DisplayName("메뉴 목록 조회")
    void menuList(){
        List<MenuDto> result = service.selectList();

        if(!result.isEmpty()){
            for(MenuDto dto : result){
                System.out.println("asdf "+ dto.getMenuNm()+", "+dto.getUrl()+", "+dto.getMenuType());
            }
        }

    }

}