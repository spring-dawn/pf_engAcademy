package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String url;
    private String menuNm;
    private int order;
    private Character useYn;
    //    private Menu parent;    // 상위 메뉴
    private List<MenuDto> children;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Req {
        private String url;
        private String menuNm;
        private int order;
        private String parentUrl;
    }

}
