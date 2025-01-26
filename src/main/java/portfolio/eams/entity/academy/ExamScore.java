package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.cglib.core.Local;
import portfolio.eams.entity.CommonEntity;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DIM_EXAM_SCORE_T")
public class ExamScore extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_SCORE_NO")
    private Long id;

    @Column(name = "EXAM_YMD", nullable = false)
    @Comment("시험일자")
    private LocalDate examDt;

    @Column(name = "EXAM_TYPE", length = 10, nullable = false)
    @Comment("시험 분류 코드. 중간/기말, 텀 테스트, 단어, 독해... 등")
    private String examType;

    @Column(name = "PERPECT_SCORE")
    @Comment("만점 기준. 디폴트 100")
    @ColumnDefault("'100.0'")
    private Double perfectScore;

    @Column(name = "STUDENT_SCORE")
    @Comment("학생의 실제 득점")
    private Double studentScore;

    @Column(name = "MEMO", length = 100)
    @Comment("비고")
    private String memo;

    // 학생 1 : 성적 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_NO")
    @Comment("학생 번호")
    private Student student;


}
