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
@Table(name = "DIM_LEARNING_CLASS_T")
public class LearningClass extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEARNING_CLASS_NO")
    private Long id;

    @Column(name = "USER_ID", length = 20)
    @Comment("담임 강사(사용자 ID)")
    private String userId;

    @Column(name = "CLASS_NM", length = 50, nullable = false, unique = true)
    @Comment("학급명")
    private String classNm;

    @Column(name = "LV_SCHOOL", length = 10, nullable = false)
    @Comment("학교 단계. 초, 중, 고등부...")
    private String lvSchool;

    // 강좌가 아니라 학급에 단계를 매기는가...
    @Column(name = "LV_LEARNING", length = 10, nullable = false)
    @Comment("수강생 학습 단계")
    private String lvLearning;

    @Column(name = "LV_DIFFICULTY", nullable = false)
    @Comment("강의 난이도")
    private int lvDifficulty;

    @Column(name = "SORT_ORDER", length = 1)
    @Comment("학급 순서. A, B, C... 생성 순서 사용")
    private Character order;

    @Column(name = "PRIOR_YN", length = 1, nullable = false)
    @Comment("Y: 선행반, N: 일반")
    @ColumnDefault("'N'")
    private Character priorYn;

    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "CAPACITY")
    @Comment("수용인원. 디폴트 13")
    @ColumnDefault("'13'")
    private int capacity;

    @Column(name = "MEMO", length = 200)
    @Comment("비고")
    private String memo;


}
