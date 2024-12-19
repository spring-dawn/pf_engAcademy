package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.eams.entity.system.Auth;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RoleDto {
    private Long id;
    private String roleNm;
    private String desc;
    private Character useYn;
    private int order;
    private List<RoleAuthDto> roleAuthList;


    // insert, update
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Req {
        private String roleNm;
        private String desc;
//        private Character useYn;
        private int order;
        private List<Long> authIdList;
    }
}
