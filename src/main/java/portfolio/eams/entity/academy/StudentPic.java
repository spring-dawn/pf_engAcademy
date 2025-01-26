package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.entity.CommonEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_STUDENT_PIC_T")
public class StudentPic extends CommonEntity {
    // 학생 증명사진 저장

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIC_NO")
    private Long id;

    @Column(name = "PIC_NM", length = 100, nullable = false)
    @Comment("원본 이미지 파일명")
    private String picNm;

    @Column(name = "PIC_NM_STORED", length = 100, nullable = false)
    @Comment("이미지 파일 저장명. UUID 사용")
    private String picNmStored;

    @Column(name = "PIC_EXT", length = 10)
    @Comment("이미지 파일 확장자")
    private String ext;

    @Column(name = "MIME_TYPE", length = 20)
    @Comment("파일 mime")
    private String mime;

    @Column(name = "PIC_PATH", length = 50, nullable = false)
    @Comment("파일 저장경로")
    private String picPath;

    // 학생 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_NO")
    @Comment("학생 번호")
    private Student student;


}
