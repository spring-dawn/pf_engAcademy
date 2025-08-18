package portfolio.eams.entity.system;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.dto.system.AuthDto;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SYS_AUTH_T")
public class Auth {
    /*
    메뉴 접근 권한. 사용자가 제어할 수 없는 부분. 모든 메뉴에 대한 조회, 편집(세분화 가능)권.
    최소 권한은 조회이므로 사용자 역할 중 조회권이 없는 메뉴는 출력되지 않음.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_NO")
    private Long id;

//    @Column(name = "AUTH_TYPE", length = 1, nullable = false)
//    @Comment("(실험적)권한 종류. C 등록, R 조회(디폴트), U 수정, D 삭제... ")
//    private Character type;

    @Column(name = "ACS_TYPE", length = 1, nullable = false)
    @Comment("W: 등록수정삭제 등 편집권한, R: 조회권한(Read)")
    private Character accessType;


    /*
    Menu : Auth = 1 : N
    단방향 매핑. 어떤 메뉴에 대한 권한인지를 표기.
     */
    @JoinColumn(name = "MENU_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("참조하는 메뉴 번호")
    private Menu menu;

    /*
    Role : Auth = N : M
     */
    @JsonIgnore
    @OneToMany(mappedBy = "auth")
    private List<RoleAuth> roles = new ArrayList<>();


    // res
//    public AuthDto toRes() {
//        return new AuthDto(id, type, menu.getUrl(), writeYn);
//    }
    public AuthDto toRes() {
        return new AuthDto(id, menu.getUrl(), accessType);
    }

}
