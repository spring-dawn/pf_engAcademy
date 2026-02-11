package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.BasicEntityColumn;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DIM_EXM_SCR_T")
public class ExamScore extends BasicEntityColumn{
    // 수강생 (시험)성적 관리
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXM_SCR_NO")
    private Long id;

    @Column(name = "EXM_YMD", nullable = false)
    @Comment("시험일자")
    private LocalDate examDt;

    @Column(name = "EXM_TYPE", length = 10, nullable = false)
    @Comment("시험 종류. e.g) 중간/기말고사, 텀테스트, 쪽지시험 등. 자체 분류 따름")
    private String examType;

    @Column(name = "SBJCT", length = 10, nullable = false)
    @Comment("시험 과목. 국, 영, 수 등 과목 대분류")
    private String subject;

    @Column(name = "SBJCT_DTL", length = 10)
    @Comment("시험 내용. 각 과목별 구체적인 분류. e.g) 문법, 독해...")
    private String subjectDetail;

    @Column(name = "MAX_SCR")
    @Comment("만점 기준. 기본 100점이나 점수 단위가 다른 경우 소수점 허용")
    @ColumnDefault("'100.0'")
    private Double maxScore;

    @Column(name = "SCR")
    @Comment("수강생의 실제 취득 점수. 만점 기준보다 클 수 없음")
    private Double score;

    @Column(name = "MEMO", length = 400)
    @Comment("비고")
    private String memo;

    // 수강생 fk TODO 인덱싱
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_NO", nullable = false)
    @Comment("성적 보유 수강생 FK")
    private Student student;



    // TODO update

    // TODO res

}
