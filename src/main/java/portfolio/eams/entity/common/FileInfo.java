package portfolio.eams.entity.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.CommonEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DIM_FILE_INFO_T")
public class FileInfo extends CommonEntity {
    // 전역 파일 관리 테이블.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_INFO_NO")
    private Long id;

    @Column(name = "FILE_NM", length = 150, nullable = false)
    @Comment("파일 원본명")
    private String fileNm;

    @Column(name = "FILE_NM_STORED", length = 200)
    @Comment("파일 저장명. UUID 사용.")
    private String fileNmStored;

    @Column(name = "FILE_PATH", length = 100, nullable = false)
    @Comment("파일 저장 경로")
    private String filePath;

    @Column(name = "FILE_EXT", length = 10)
    @Comment("파일 확장자")
    private String fileExt;

    @Column(name = "FILE_MIME", length = 20)
    @Comment("파일 MIME type")
    private String fileMime;


    // update

    //


}
