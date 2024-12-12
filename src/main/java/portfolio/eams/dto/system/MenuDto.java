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
    private int order;
    private Character useYn;
    private Long parentId;
    private List<MenuDto> children;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        private String url;
        private String menuNm;
        private int order;
        private String parentUrl;
    }

}
