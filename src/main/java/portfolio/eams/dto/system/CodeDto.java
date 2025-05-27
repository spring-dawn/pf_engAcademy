package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.eams.entity.system.Code;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CodeDto {
    // code 는 단순한 값이지만 불변을 보장하기엔 사용처가 애매함
    private Long id;
    private String cd;
    private String cdVal;
    private String memo;
    private Character useYn;
    private Character lastYn;
    private Character sysYn;
    private Long parentId;
    private List<Code> children;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        // insert, update
        private String cd;
        private String cdVal;
        private String memo;
        private Character useYn;
        private Character lastYn;
        private Character sysYn;
    }
}
