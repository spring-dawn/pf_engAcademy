package portfolio.eams.service.system;

import portfolio.eams.dto.system.RoleDto;

public interface RoleService {


    /**
     *
     * @param req
     * @return
     */
    RoleDto createRole4Init(RoleDto.Req req);


    /**
     *
     * @param req
     * @return
     */
    RoleDto updateRole(RoleDto.Req req);

}
