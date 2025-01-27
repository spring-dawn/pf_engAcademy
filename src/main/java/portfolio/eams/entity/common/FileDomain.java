package portfolio.eams.entity.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FCT_FILE_DOMAIN_T")
public class FileDomain {
    // 첨부파일 N : 각 업로드 게시판 M. 그 사이를 잇는 연결 테이블.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_DOMAIN_NO")
    private Long id;

    // 연관관계
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_INFO_NO")
    @Comment("참조하는 파일관리 번호")
    private FileInfo fileInfo;

    // 각 도메인 fk 가 무한정 늘어나도록 할 수 없으므로, 코드값을 지정하여 식별
    @Column(name = "DOMAIN_NO")
    @Comment("참조하는 도메인 내부 번호")
    private Long domainPk;

    @Column(name = "DOMAIN_TYPE", length = 3)
    @Comment("첨부파일 출처 도메인 식별코드 3자리")
    private Character domainType;


}
