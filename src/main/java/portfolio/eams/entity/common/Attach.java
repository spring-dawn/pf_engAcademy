package portfolio.eams.entity.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.BasicEntityColumn;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FCT_ATCH_T")
public class Attach extends BasicEntityColumn {
    // 전역 첨부파일 관리 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATCH_NO")
    private Long id;

    @Column(name = "ATCH_NM", length = 400, nullable = false)
    @Comment("파일 원본명")
    private String fileNm;

    @Column(name = "ATCH_NM_STORED", length = 200, nullable = false, unique = true)
    @Comment("파일 저장명. UUID 사용.")
    private String fileNmStored;

    @Column(name = "ATCH_PATH", length = 200, nullable = false)
    @Comment("파일 저장 경로")
    private String path;

    @Column(name = "EXT", length = 10)
    @Comment("파일 확장자")
    private String ext;

    @Column(name = "MIME", length = 20)
    @Comment("파일 MIME type")
    private String mimeType;

    @Column(name = "ATCH_SIZE")
    @Comment("파일 용량")
    private Long size;

    // TODO res


}
