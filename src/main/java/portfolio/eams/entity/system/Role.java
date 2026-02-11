package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.dto.system.RoleDto;
import portfolio.eams.entity.BasicEntityColumn;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "SYS_ROLE_T")
public class Role extends BasicEntityColumn {
    /*
    사용자 역할(권한 컨테이너). 사용자가 실제로 제어 가능한 부분은 이 엔티티로 제한.
    241222 CommonEntity 상속 받으면 DynamicInsert 가 안 먹히는 이슈 있음
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_NO")
    private Long id;

    @Column(name = "ROLE_NM", length = 150, nullable = false)
    @Comment("직급명")
    private String roleNm;

    @Column(name = "SORT_SEQ", nullable = false)
    @Comment("정렬 순서. 숫자가 작을수록 우선")
    @ColumnDefault("'0'")
    private Integer sortSeq;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("사용여부. Y: 사용, N: 미사용")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "SYS_DFLT_YN", length = 1, nullable = false)
    @Comment("시스템 기본 권한이면(Y) 삭제 불가")
    @ColumnDefault("'N'")
    private Character sysDefaultYn;

    @Column(name = "VSBL_YN", length = 1, nullable = false)
    @Comment("조회가능여부. N이면 DB에서만 조회 가능")
    @ColumnDefault("'Y'")
    private Character visibleYn;

    @Column(name = "MEMO", length = 200)
    @Comment("비고")
    private String memo;


    /*
    연관관계
     */
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MenuRoleAccess> menuRoleAccessList = new ArrayList<>();


    // update
    public void update(RoleDto.Req req) {
        roleNm = req.getRoleNm();
        sortSeq = req.getSortSeq();
        memo = req.getMemo();
    }



    // res
    public RoleDto toRes() {
        return RoleDto.builder()
                .id(id)
                .roleNm(roleNm)
                .memo(memo)
                .sortSeq(sortSeq)
                .useYn(useYn)
                .build();
    }


}
