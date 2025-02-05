package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.entity.CommonEntity;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DIM_TIMETABLE_T")
public class TimeTable extends CommonEntity {
    // *분기별 강의 시간표

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMETABLE_NO")
    private Long id;

    @Column(name = "QUARTER", nullable = false)
    @Comment("분기. 1~4 분기 중 하나로 0은 없음.")
    private int quarter;

    @Column(name = "YEAR")
    @Comment("연도")
    private int year;

    @Column(name = "START_YMD")
    @Comment("시간표 적용 시작일. e.g.) 2024-11-04(4분기)")
    private LocalDate startDt;

    @Column(name = "END_YMD")
    @Comment("시간표 적용 종료일. e.g.) 2025-02-28(4분기)")
    private LocalDate endDt;


    // 학급 1 : 시간표 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEARNING_CLASS_NO")
    @Comment("참조하는 학급 번호")
    private LearningClass learningClass;


    // update

    // res


}
