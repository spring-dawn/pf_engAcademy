package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class MenuDto {
    private Long id;
    private String url;
    private String menuNm;
    private Integer depth;
    private Integer sortSeq;
    private Character useYn;
    private Long parentId;
    private List<MenuDto> children;


    // insert, update... menu 는 사용자 제어가 아니어서 약식 req.
    @Data
    @AllArgsConstructor
    public static class Req {
        private String url;
        private String menuNm;
        private Integer depth;
        private Integer sortSeq;
        private String parentUrl;
    }

}
