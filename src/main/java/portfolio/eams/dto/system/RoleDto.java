package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RoleDto {






    // insert, update
    @Data
    @NoArgsConstructor
    public static class Req {
        private String roleNm;
        private String desc;
        private int order;

    }
}
