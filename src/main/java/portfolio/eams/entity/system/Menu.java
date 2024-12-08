package portfolio.eams.entity.system;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import portfolio.eams.dto.system.MenuDto;
import portfolio.eams.entity.CommonEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SYS_MENU_T")
public class Menu extends CommonEntity {
    /*
    메뉴. 탭, 모달 개념 등은 프론트에서 관리.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_NO")
    private Long id;

    @Column(name = "MENU_URL", unique = true, length = 200)
    @Comment("메뉴 주소")
    private String url;

    @Column(name = "MENU_NM", length = 20)
    @Comment("메뉴명")
    private String menuNm;

    @Column(name = "SORT_ORDER")
    @Comment("정렬순서. 작을수록 우선")
    private int order;

    @Column(name = "USE_YN")
    @Comment("사용여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    // 트리 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_MENU_NO")
    @Comment("참조하는 상위 메뉴")
    private Menu parent;    // 상위 메뉴

    @OneToMany(mappedBy = "parent")
    private List<Menu> children = new ArrayList<>();    // 하위 메뉴들.


    public MenuDto toRes() {
        return MenuDto.builder()
                .id(id)
                .url(url)
                .menuNm(menuNm)
                .order(order)
                .useYn(useYn)
                // npe 발생하는지 확인 필요
                .children(children.stream().map(Menu::toRes).toList())
                .build();
    }

}
