package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.eams.entity.system.CommonCode;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommonCodeDto {
    private Long id;
    private String cd;
    private String val;
    private String rmrk;
    private Character useYn;
    private Integer depth;
    private Integer sortSeq;
    private Long parentId;
    private List<CommonCode> children;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        // insert, update
        private String cd;
        private String val;
        private String rmrk;
        private Integer depth;
        private Integer sortSeq;
        private Character useYn;
    }
}
