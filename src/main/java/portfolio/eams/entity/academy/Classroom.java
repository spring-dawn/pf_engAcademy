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
@Table(name = "DIM_CLSRM_T")
public class Classroom extends BasicEntityColumn {
    // 학급(반) 관리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLSRM_NO")
    private Long id;

    @Column(name = "CLSRM_NM", length = 250, nullable = false)
    @Comment("학급명")
    private String classNm;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("운영 여부")
    private Character useYn;

    @Column(name = "ALL_IN_ONE_YN", length = 1, nullable = false)
    @Comment("종합반 여부. N이면 단과반")
    private Character allInOneYn;

    @Column(name = "NTV_YN", length = 1, nullable = false)
    @Comment("원어 강의 유무. 외국어 수업 등의 옵션")
    private Character nativeYn;

    @Column(name = "PRE_YN", length = 1, nullable = false)
    @Comment("선행학습 여부. Y면 특정 단계의 선행학습반")
    private Character preYn;

    @Column(name = "PRE_WHAT", length = 10)
    @Comment("PRE_YN 값이 Y인 경우 입력. 선행학습 목표 대상 e.g) pre-middle: 중등 선행")
    private String preWhat;

    @Column(name = "TRGT_STEP", length = 10)
    @Comment("수강생 단계. 미취학~성인 등 수강생 소속 분류")
    private String targetStep;

    @Column(name = "TRGT_LV", length = 10)
    @Comment("수강생 학습수준. 초/중/고급 분류")
    private String targetLv;

    @Column(name = "COURSE_LV")
    @Comment("강의 난이도 1~3. 값이 클수록 고급 수준의 강의 배치")
    private Integer courseLv;

    @Column(name = "CAPACITY")
    @Comment("수용 정원 수")
    @ColumnDefault("'13'")
    private Integer capacity;

    @Column(name = "SORT_SEQ")
    @Comment("분류가 동일한 학급의 정렬 순서. 단순 학급 개수 구분용에 가까우며 a~z 알파벳 순서에 대응하여 적용")
    private Integer sortSeq;

    @Column(name = "CLSRM_LOC")
    @Comment("사용 교실. 학급 위치 등")
    private String classroomLoc;

    @Column(name = "MEMO", length = 400)
    @Comment("비고")
    private String memo;


    // TODO 인덱싱?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    @Comment("담임 강사 fk")
    private User user;
    

    // TODO update

    // TODO res
}
