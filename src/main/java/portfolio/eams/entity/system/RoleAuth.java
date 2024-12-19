package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import portfolio.eams.dto.system.RoleAuthDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FCT_ROLE_AUTH_T")
public class RoleAuth {
    /*
    Role : Auth = N : M
    다대다 해소용 연결 테이블
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_AUTH_NO")
    private Long id;

    /*
    연관관계
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NO")
    private Role role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTH_NO")
    private Auth auth;


    // res
    public RoleAuthDto toRes() {
        return new RoleAuthDto(id, auth.getId(), auth.getMenu().getUrl(), auth.getType());
    }

}
