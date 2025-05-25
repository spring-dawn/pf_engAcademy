package portfolio.eams.repo.mybatis;

import org.apache.ibatis.annotations.Mapper;
import portfolio.eams.dto.system.UserDto;
import portfolio.eams.entity.system.User;

import java.util.List;

@Mapper
public interface UserMapper {
    // mybatis 테스트용 매퍼 인터페이스
    List<UserDto> getUserList();

}
