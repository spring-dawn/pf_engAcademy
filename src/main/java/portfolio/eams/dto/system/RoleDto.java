package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RoleDto {
    private Long id;
    private String roleNm;
    private Character useYn;
    private Character sysDefaultYn;
    private Character visibleYn;
    private Integer sortSeq;
    private String memo;

    private List<MenuRoleAccessDto> acsList;


    // insert, update
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Req {
        private String roleNm;
        private Character useYn;
        private Character sysDefaultYn;
        private Character visibleYn;
        private Integer sortSeq;
        private String memo;
    }
}
