package portfolio.eams.service.system;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.repo.mybatis.UserMapper;

import java.util.List;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService service;

    @Test
    @DisplayName("mybatis 연동 테스트")
    public void getUserTest() {
        List<UserDto> list = service.getUserTest();

        for (UserDto dto : list) {
            System.out.println("asdf userId=" + dto.getUserId());
        }

    }

}