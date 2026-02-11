package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.BasicEntityColumn;
import portfolio.eams.entity.system.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FCT_CLSRM_COURSE_T")
public class Course extends BasicEntityColumn {
    // 학급별 제공 강의 목록

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_NO")
    private Long id;

    @Column(name = "SBJCT", length = 10, nullable = false)
    @Comment("강의 과목: 국, 영, 수 등 대분류")
    private String subject;

    @Column(name = "SBJCT_DTL", length = 10)
    @Comment("강의 내용 세부 분류 e.g) 파닉스, 문법, 독해, 듣기 등")
    private String subjectDetail;

    @Column(name = "COURSE_MTHD", length = 10)
    @Comment("강의 방식. e.g) 일반, 연극/뮤지컬, 대화... 분류코드 사용")
    private String courseMethod;

    @Column(name = "MAX_HRS")
    @Comment("강의 시수. 시간표 배치 시 강의별 총 시수를 넘을 수 없음")
    private Integer maxHours;

    @Column(name = "RQRD_YN", length = 1, nullable = false)
    @ColumnDefault("'N'")
    @Comment("필수 수강 여부. N이면 선택 과목")
    private Character requiredYn;

    @Column(name = "EXM_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    @Comment("시험 있음: Y. 때로 시험을 치르지 않는 P/F 과목도 존재")
    private Character examYn;

//    @Column(name = "SORT_SEQ")
//    @Comment("정렬 순서")
//    private Integer sortSeq;

    @Column(name = "TEXTBOOK", length = 200)
    @Comment("교재 등 수업 준비물 기재")
    private String textbook;

    @Column(name = "MEMO", length = 250)
    @Comment("비고")
    private String memo;

    // 학급, 강사 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLSRM_NO", nullable = false)
    @Comment("학급 fk")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", nullable = false)
    @Comment("강사 fk")
    private User user;

    // TODO update

    // TODO res

}
