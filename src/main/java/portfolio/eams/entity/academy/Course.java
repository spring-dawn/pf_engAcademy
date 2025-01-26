package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.entity.CommonEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert
@Table(name = "DIM_COURSE_T")
public class Course extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_NO")
    private Long id;

    @Column(name = "COURSE_NM", length = 100, unique = true, nullable = false)
    @Comment("강의명")
    private String courseNm;

    @Column(name = "DESCRIPTION", length = 100)
    @Comment("강의 소개")
    private String description;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("사용여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "NATIVE_YN", length = 1, nullable = false)
    @Comment("원어민 강사 유무")
    @ColumnDefault("'N'")
    private Character nativeYn;

    @Column(name = "COURSE_TYPE", length = 10, nullable = false)
    @Comment("강의 분류 코드. 파닉스, 문법, 독해... 등")
    private String courseType;

    @Column(name = "METHOD", length = 10, nullable = false)
    @Comment("강의 방식 코드. 일반, 뮤지컬, 원서, 과외... 등")
    private String method;

    @Column(name = "TEXTBOOK", length = 200)
    @Comment("교재 포함 준비물 기재")
    private String textbook;


}
