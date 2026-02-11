package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.dto.system.MenuRoleAccessDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "FCT_MENU_ROLE_ACS_T",
        indexes = @Index(name = "idx_acs", columnList = "ROLE_NO, MENU_NO")
)
public class MenuRoleAccess {
    /*
    MENU : ROLE 권한 연결 테이블
    기존의 AUTH 테이블로 사용하던 방식을 축소함.

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
    @Comment("참조하는 직급 번호")
    private Role role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_NO", nullable = false)
    @Comment("참조하는 메뉴 번호")
    private Menu menu;


    // 실제 권한 체크
    @Column(name = "READ_YN", length = 1, nullable = false)
    @Comment("조회권 유무")
    private Character readYn;

    @Column(name = "WRITE_YN", length = 1, nullable = false)
    @Comment("편집권(쓰기 권한) 유무")
    private Character writeYn;

    @Column(name = "SPARE_YN", length = 1)
    @Comment("권한 확장에 대비한 여분 _yn 컬럼")
    private Character spare;


    // update 이 경우는 '수정'의 개념이 삭제-추가가 아니라 진짜로 '변경'의 의미
    public void update(MenuRoleAccessDto dto) {
        readYn = dto.readYn();
        writeYn = dto.writeYn();
    }


    // res
    public MenuRoleAccessDto toRes() {
        return new MenuRoleAccessDto(id, role.getId(), menu.getId(), readYn, writeYn);
    }


}
