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
    // code 는 단순한 값이지만 불변을 보장하기엔 사용처가 애매함
    private Long id;
    private String cd;
    private String cdVal;
    private String memo;
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
        private String cdVal;
        private String memo;
        private Integer depth;
        private Integer sortSeq;
        private Character useYn;
    }
}
