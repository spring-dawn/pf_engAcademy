package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.entity.CommonEntity;
import portfolio.eams.dto.system.UserDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_USER_T")
public class User extends CommonEntity implements UserDetails {
    /*
    사용자: 학원 운영 간부, 평 강사. 모든 직원은 강사임을 전제.
    포트폴리오이니만큼 단순하게 받음
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Long id;

    @Column(name = "USER_ID", length = 20, unique = true, nullable = false)
    @Comment("사용자 ID")
    private String userId;

    @Column(name = "USER_PW", length = 200, nullable = false)
    @Comment("사용자 비밀번호")
    private String userPw;

    @Column(name = "SALT", length = 30, nullable = false)
    @Comment("암호화 솔트")
    private String salt;

    @Column(name = "USER_NM", length = 20, nullable = false)
    @Comment("사용자명")
    private String userNm;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("재직여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "ADM_YN", length = 1, nullable = false)
    @Comment("관리자 여부. 개발자 등 마스터계정, 삭제 불가.")
    @ColumnDefault("'N'")
    private Character admYn;

    @Column(name = "TEL", length = 20)
    @Comment("연락처")
    private String tel;

    @Column(name = "EML", length = 100, unique = true)
    @Comment("이메일")
    private String email;

    @Column(name = "JOIN_YMD")
    @Comment("입사일")
    private LocalDate joinYmd;

    @Column(name = "QUIT_YMD")
    @Comment("퇴사일")
    private LocalDate quitYmd;

    @Column(name = "LGN_FAIL_CNT")
    @Comment("로그인 실패 횟수")
    @ColumnDefault("'0'")
    private int loginFailCnt;

    @Column(name = "PW_MOD_YMD")
    @Comment("최신 비밀번호 변경일")
    private LocalDate pwModYmd;

    /*
      User : Role = N : 1 단방향 참조.
      연관 필드에는 반드시 @JsonIgnore 등 순환참조 방어 어노테이션을 붙입니다
      @ManyToOne 의 기본 fetch 전략은 EAGER 이므로 LAZY 를 명시해줍니다.
      양방향 매핑은 가능한 한 피하고, 필요한 만큼만 연관 짓습니다.
   */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NO")
    @Comment("사용 중인 역할 번호")
    private Role role;


    /*
    UserDetails 구현
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(this.getRole().getId().toString()));
//        return authorities;
        return List.of(new SimpleGrantedAuthority(role.getId().toString()));
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return loginFailCnt < 5;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return useYn.equals('Y');
    }


    // update
    public void updateLoginFailCnt() {
        loginFailCnt += 1;
    }

    public void initLoginFailCnt() {
        loginFailCnt = 0;
    }

    // 변경 가능한 부분 제한
    public void updateUser(UserDto.UpdateReq req) {
        userNm = req.getUserNm();
        useYn = req.getUseYn();
        tel = req.getTel();
        email = req.getEmail();
    }

    // 역할 변경. 캐시 삭제 불필요.
    public void updateUserRole(Role role) {
        this.role = role;
    }

    public void quitUser(UserDto.QuitReq req) {
        useYn = 'N';
        quitYmd = LocalDate.parse(req.getQuitYmd());
    }



    // res
    public UserDto toRes() {
        return UserDto.builder()
                .build();
    }


}
