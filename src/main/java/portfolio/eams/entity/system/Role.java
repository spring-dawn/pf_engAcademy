package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.CommonEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SYS_ROLE_T")
public class Role extends CommonEntity {
    /*
    사용자 역할(권한 컨테이너). 사용자가 실제로 제어 가능한 부분은 이 엔티티로 제한.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_NO")
    private Long id;

    @Column(name = "ROLE_NM", length = 10)
    @Comment("역할(직책)명")
    private String roleNm;

    @Column(name = "DESC", length = 50)
    @Comment("역할 내용 상세")
    private String desc;

    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부. Y: 사용, N: 미사용(출력x)")
    @ColumnDefault("'Y'")
    private Character useYn;


    /*
    연관관계
     */
    @JsonIgnore
    @OneToMany
    private List<RoleAuth> authList = new ArrayList<>();


    // update
    public void update(){
    }


    // res


}