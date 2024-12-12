package portfolio.eams.entity.system;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;


@Getter
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
    @Column(name = "ROLE_NO")
    private Long id;

//    @Column(name = "AUTH_TYPE", length = 5, nullable = false)
//    @Comment("권한 종류. C, R, U, D... ")
//    private String type;

    @Column(name = "WRITE_YN", length = 1, nullable = false)
    @Comment("편집권 여부. Y: 편집 가능 권한, N: 조회권")
    private Character writeYn;


    /*
    Menu : Auth = 1 : N
    단방향 매핑
     */
    @JoinColumn(name = "MENU_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;


}
