package portfolio.eams.entity.academy;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FCT_CLSRM_TMTBL_DTL_T")
public class TimetableDetail {
    // 강의 시간표 세부계획

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TMTBL_DTL_NO")
    private Long id;

    @Column(name = "WHEN_DAY")
    @Comment("강의 요일. 1~7 표준요일 Enum 기준 사용")
    private Integer courseDay;

    @Column(name = "COURSE_MINS")
    @Comment("강의 소요시간(분)")
    private Integer mins;

    @Column(name = "WHEN_START_TM")
    @Comment("강의 시작시간. e.g) 9:00~21:00 시간 범위")
    private LocalTime startTm;

    
    // fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TMTBL_NO", nullable = false)
    @Comment("시간표 fk")
    private Timetable timetable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_NO", nullable = false)
    @Comment("강의 fk")
    private Course course;


    
}
