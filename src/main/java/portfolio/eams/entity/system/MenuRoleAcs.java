package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.dto.system.MenuRoleAcsDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "FCT_MENU_ROLE_ACS_T",
        indexes = @Index(name = "idx_acs", columnList = "ROLE_NO, MENU_NO")
)
public class MenuRoleAcs {
    /*
    MENU : ROLE 권한 연결 테이블
    기존의 AUTH 테이블로 사용하던 방식을 축소.
    매핑된 그 자체로 조회권이 있다고 판단, 반드시 조회권이 선행해야 기타 권한 부여 가능.

    본래 설계대로는 복합 키로 구성해야 하나 jpa 사용 시 복합 키 구현이 까다로워 편의상 fk 사용
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ACS_NO")
    private Long id;

    /*
    연관관계
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NO", nullable = false)
    @Comment("참조하는 권한 번호")
    private Role role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_NO", nullable = false)
    @Comment("참조하는 메뉴 번호")
    private Menu menu;


    // 실제 권한 체크
    @Column(name = "ACT_YN", length = 1, nullable = false)
    @Comment("데이터 활성화 체크권한")
    private Character actYn;

    @Column(name = "CU_YN", length = 1, nullable = false)
    @Comment("등록, 수정 편집권 권한")
    private Character cuYn;

    @Column(name = "D_YN", length = 1, nullable = false)
    @Comment("삭제 권한")
    private Character dYn;


    // update
    public void update(MenuRoleAcsDto dto) {
        actYn = dto.actYn();
        cuYn = dto.cuYn();
        dYn = dto.dYn();
    }


    // res
    public MenuRoleAcsDto toRes() {
        return new MenuRoleAcsDto(id, role.getId(), menu.getId(), actYn, cuYn, dYn);
    }


}
