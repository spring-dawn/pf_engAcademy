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
@Table(name = "FCT_FILE_INFO_LINK_T")
public class FileInfoLink {
    // 첨부파일 N : 각 업로드 게시판 M. 그 사이를 잇는 연결 테이블.
    // 물리적인 연관관계가 없는 내용을 JPA 내장 쿼리로 사용하면 세타 조인이 일어날 수 있으므로 지양. MyBatis 사용 권장.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_INFO_LINK_NO")
    private Long id;

    // 연관관계
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_INFO_NO")
    @Comment("참조하는 파일관리 번호")
    private FileInfo fileInfo;

    // 각 도메인 fk 가 무한정 늘어나도록 할 수 없으므로, 코드값을 지정하여 식별
    @Column(name = "LINK_DOMAIN_NO")
    @Comment("논리적으로 참조하는 도메인 내부 번호")
    private Long domainPk;

    @Column(name = "LINK_DOMAIN_TYPE", length = 3)
    @Comment("첨부파일 출처 도메인 식별코드 3자리")
    private Character domainType;


}
