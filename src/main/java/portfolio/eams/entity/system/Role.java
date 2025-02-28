package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.dto.system.RoleDto;
import portfolio.eams.entity.CommonEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "SYS_ROLE_T")
public class Role {
    /*
    사용자 역할(권한 컨테이너). 사용자가 실제로 제어 가능한 부분은 이 엔티티로 제한.
    241222 CommonEntity 상속 받으면 DynamicInsert 가 안 먹히는 이슈 있음
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_NO")
    private Long id;

    @Column(name = "ROLE_NM", length = 10)
    @Comment("역할(직책)명")
    private String roleNm;

    @Column(name = "DESCRIPTION", length = 50)
    @Comment("역할 내용 상세")
    private String desc;

    @Column(name = "SORT_ORDER")
    @Comment("역할 정렬 순서")
    private int order;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("사용여부. Y: 사용, N: 미사용")
    @ColumnDefault("'Y'")
    private Character useYn;


    /*
    연관관계
     */
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<RoleAuth> roleAuthList = new ArrayList<>();


    // update
    public void update(RoleDto.Req req) {
        roleNm = req.getRoleNm();
        desc = req.getDesc();
        order = req.getOrder();
    }

    public void addAuthList(List<RoleAuth> roleAuthList) {
        this.roleAuthList = roleAuthList;
    }


    // res
    public RoleDto toRes() {
        return RoleDto.builder()
                .id(id)
                .roleNm(roleNm)
                .desc(desc)
                .order(order)
                .useYn(useYn)
                .roleAuthList(roleAuthList == null ? null : roleAuthList.stream().map(RoleAuth::toRes).toList())
                .build();
    }


}
