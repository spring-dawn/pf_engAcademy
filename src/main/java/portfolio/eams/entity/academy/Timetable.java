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
@Table(name = "FCT_CLSRM_TMTBL_T")
public class Timetable extends BasicEntityColumn {
    // 학급별 강의 시간표(메타데이터)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TMTBL_NO")
    private Long id;

    @Column(name = "START_YMD", nullable = false)
    @Comment("시간표 적용 시작일")
    private LocalDate startDt;

    @Column(name = "END_YMD", nullable = false)
    @Comment("시간표 적용 종료일")
    private LocalDate endDt;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @ColumnDefault("'N'")
    @Comment("사용 여부. 같은 기간/분기 안에 1개만 활성화")
    private Character useYn;

    // fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLSRM_NO", nullable = false)
    @Comment("학급 fk")
    private Classroom classroom;


    // TODO update

    // TODO res
}
