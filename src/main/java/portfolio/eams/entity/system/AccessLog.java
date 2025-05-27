package portfolio.eams.entity.system;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.CommonEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SYS_ACS_LOG_T")
public class AccessLog extends CommonEntity {
    /*
    사용자 로그인 시 접속일시, 접속 ip, 접속 환경(브라우저) 등 기록
    로그아웃 시점까지 체크하긴 어려울 것 같다.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACS_LOG_NO")
    private Long id;

    @Column(name = "ACS_ID", length = 50)
    @Comment("접속 ID")
    private String acsId;

    @Column(name = "ACS_NM", length = 50)
    @Comment("접속자명")
    private String acsNm;

    @Column(name = "ACS_ROLE_NM", length = 50)
    @Comment("접속자 권한명")
    private String acsRoleNm;

    @Column(name = "ACS_SESSION_ID", length = 100)
    @Comment("세션 ID")
    private String acsSessionId;

    @Column(name = "ACS_IP", length = 100)
    @Comment("접속 IP")
    private String acsIP;

    @Column(name = "AGENT", length = 200)
    @Comment("접속 환경: 브라우저")
    private String acsAgent;


}
