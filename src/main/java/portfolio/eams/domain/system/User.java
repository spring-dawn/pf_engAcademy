package portfolio.eams.domain.system;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import portfolio.eams.domain.CommonEntity;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DIM_USER_T")
public class User extends CommonEntity implements UserDetails {
    /*
    사용자: 학원 운영 간부, 평 강사. 모든 직원은 강사임을 전제.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Long id;

    @Column(name = "USER_ID", length = 20)
    @Comment("사용자 ID")
    private String userId;

    @Column(name = "USER_PW", length = 200)
    @Comment("사용자 비밀번호")
    private String userPw;

    @Column(name = "SALT", length = 20)
    @Comment("암호화 솔트")
    private String salt;

    @Column(name = "USER_NM", length = 20)
    @Comment("사용자명")
    private String userNm;

    @Column(name = "USE_YN", length = 1)
    @Comment("재직여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "ADM_YN", length = 1)
    @Comment("")
    @ColumnDefault("'N'")
    private Character admYn;

    @Column(name = "TEL", length = 20)
    @Comment("")
    private String tel;

    @Column(name = "EML", length = 100)
    @Comment("")
    private String email;

    @Column(name = "LGN_FAIL_CNT")
    @Comment("로그인 실패 횟수")
    private int loginFailCnt;

    @Column(name = "PW_MOD_YMD")
    @Comment("최신 비밀번호 변경일")
    private LocalDate pwModYmd;

    @Column(name = "JOIN_YMD")
    @Comment("입사일")
    private LocalDate joinYmd;

    @Column(name = "QUIT_YMD")
    @Comment("퇴사일")
    private LocalDate quitYmd;



    /*
    UserDetails 구현
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
