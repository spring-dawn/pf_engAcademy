package portfolio.eams.service.system;


import portfolio.eams.dto.system.CodeDto;

public interface CodeService {
    // CRUD..

    // select api 가 특히 중요하다. 원하는 코드값을 가져와야 하니까?

    // insert
    CodeDto insertCode(CodeDto.Req req);
}
