package portfolio.eams.entity.system;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import portfolio.eams.entity.BasicEntityColumn;
import portfolio.eams.dto.system.UserDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_USER_T")
public class User extends BasicEntityColumn implements UserDetails {
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

    @Column(name = "NICK_NM", length = 30)
    @Comment("닉네임. 별도의 영어이름 등")
    private String nickNm;

    @Column(name = "LCL_YN", length = 1, nullable = false)
    @Comment("내국인 여부. N: 원어민 강사")
    @ColumnDefault("'Y'")
    private Character localYn;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("재직여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "ADM_YN", length = 1, nullable = false)
    @Comment("관리자 여부. 삭제 불가")
    @ColumnDefault("'N'")
    private Character admYn;

    @Column(name = "SYS_DEV_YN", length = 1, nullable = false)
    @Comment("유지보수 등 담당 개발자 여부. Y: 삭제 불가 및 DB에서만 조회 가능")
    @ColumnDefault("'N'")
    private Character sysDevYn;

    @Column(name = "DEL_YN", length = 1, nullable = false)
    @Comment("논리적 삭제 여부. Y: DB에서만 조회 가능")
    @ColumnDefault("'N'")
    private Character delYn;

    @Column(name = "TEL", length = 20)
    @Comment("연락처. 주로 휴대폰")
    private String tel;

    @Column(name = "EML", length = 200, unique = true)
    @Comment("이메일. 주로 업무 메일 기재")
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
    private Integer loginFailCnt;

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
    @Comment("직급 fk")
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
    public void loginFailure() {
        loginFailCnt += 1;
    }

    public void initLoginFailCnt() {
        loginFailCnt = 0;
    }

    // 일반적인 변경 범위
    public void updateUser(UserDto.UpdateReq req) {
        userNm = req.getUserNm();
        nickNm = req.getNickNm();
        tel = req.getTel();
        email = req.getEmail();
        joinYmd = LocalDate.parse(req.getJoinYmd());
    }

    // 직급 변경. 캐시 삭제 불필요.
    public void updateUserRole(Role newRole) {
        role = newRole;
    }

    public void quitUser(UserDto.QuitReq req) {
        useYn = 'N';
        quitYmd = LocalDate.parse(req.getQuitYmd());
    }

    public void deleteUserSoft(Long userId) {
        delYn = 'Y';
    }


    // res
    public UserDto toRes() {
        return UserDto.builder()
                .id(id)
                .userId(userId)
                .userNm(userNm)
                .nickNm(nickNm)
                .tel(tel)
                .email(email)
                .useYn(useYn)
                .localYn(localYn)
                .joinYmd(joinYmd)
                .roleId(role.getId())
                .roleNm(role.getRoleNm())
                .build();
    }


}
