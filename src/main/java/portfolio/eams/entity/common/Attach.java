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
@Table(name = "FCT_ATCH_INFO_T")
public class Attach extends BasicEntityColumn {
    // 전역 첨부파일 관리 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATCH_NO")
    private Long id;

    @Column(name = "ORIGIN_NM", nullable = false)
    @Comment("파일 원본명")
    private String fileNm;

    @Column(name = "STORED_NM", nullable = false, unique = true)
    @Comment("파일 저장명. UUID 사용.")
    private String fileNmStored;

    @Column(name = "STORED_PATH", length = 200, nullable = false)
    @Comment("서버 내부 저장 상대경로")
    private String path;

    @Column(name = "SIZE", nullable = false)
    @Comment("파일 용량")
    private Long size;

    @Column(name = "EXT", length = 10)
    @Comment("파일 확장자")
    private String ext;

    @Column(name = "MIME", length = 30)
    @Comment("파일 MIME type")
    private String mimeType;


    // TODO res


}
