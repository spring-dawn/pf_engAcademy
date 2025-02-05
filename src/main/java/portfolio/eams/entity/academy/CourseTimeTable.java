package portfolio.eams.entity.academy;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import portfolio.eams.util.enums.DayOfWeek;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FCT_COURSE_TIMETABLE_T")
public class CourseTimeTable {
    // 강의 N : 시간표 M 사이의 연결 테이블. 분기별 시간표의 구체적인 내역.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMETABLE_NO")
    private Long id;

    // gpt: DB 에는 숫자(0~6)로 저장되지만 코드에서는 DayOfWeek.MONDAY 처럼 사용 가능
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "COURSE_DAY", nullable = false)
    @Comment("강의 요일. enum 사용. 0:SUN ~ 6:SAT")
    private DayOfWeek courseDay;

    @Column(name = "COURSE_START_TM", nullable = false)
    @Comment("강의 시작 시간")
    private LocalTime startTm;

    @Column(name = "COURSE_END_TM", nullable = false)
    @Comment("강의 종료 시간")
    private LocalTime endTm;

    // 시간표 1 : 시간표 내역 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEARNING_CLASS_NO")
    @Comment("참조하는 학급 번호")
    private LearningClass learningClass;

    // 강의 1 : 시간표 내역 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_NO")
    @Comment("참조하는 강의 번호")
    private Course course;


}
