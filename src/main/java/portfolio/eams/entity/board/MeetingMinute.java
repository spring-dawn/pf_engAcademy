package portfolio.eams.entity.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.entity.CommonEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_MEETING_MIN_T")
public class MeetingMinute extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEETING_MIN_NO")
    private Long id;

    @Column(name = "MEETING_YMD", nullable = false)
    @Comment("회의일자")
    private LocalDate meetingDt;

    @Column(name = "START_TM")
    @Comment("시작시간")
    private LocalTime startTm;

    @Column(name = "END_TM")
    @Comment("종료시간")
    private LocalTime endTm;

    @Column(name = "ATTENDEES", length = 150)
    @Comment("참석자. 외부 인원 참석 가능성을 고려해 CC가 아닌 일반 문자열 처리.")
    private String attendees;

    @Column(name = "AGENDA", length = 150)
    @Comment("제목(회의 주제)")
    private String agenda;

    @Column(name = "CONTENTS", columnDefinition = "text")
    @Comment("내용")
    private String contents;

    // 너무 짧은가...?
    @Column(name = "DECISION", length = 200)
    @Comment("결론")
    private String decision;

    @Column(name = "TMP_YN", length = 1)
    @Comment("임시저장 여부. 임시저장 건은 게시판에 노출되지 않음.")
    @ColumnDefault("'N'")
    private Character tmpYn;

    @Column(name = "HIT")
    @Comment("조회수")
    private int hit;

    @Column(name = "IMPORTANCE")
    @Comment("중요도. 1: 낮음, 2: 보통, 3: 중요. 별 3개 기준.")
    private int importance;

    // update

    // res


}
