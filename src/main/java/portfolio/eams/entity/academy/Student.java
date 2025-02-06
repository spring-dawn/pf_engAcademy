package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.entity.CommonEntity;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_STUDENT_T")
public class Student extends CommonEntity {
    // 학생 정보 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_NO")
    private Long id;

    @Column(name = "STUDENT_NM", length = 20, nullable = false)
    @Comment("학생명")
    private String studentNm;

    @Column(name = "SCHOOL_NM", length = 30)
    @Comment("재학 중인 학교명")
    private String schoolNm;

    @Column(name = "BIRTH_YMD")
    @Comment("생년월일")
    private LocalDate birthDt;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("사용여부(현재 수강 여부)")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "LV_SCHOOL", length = 10)
    @Comment("학교 등급. ES: 초등, MD: 중등, HG: 고등학교...")
    private String lvSchool;

    @Column(name = "LV_LEARNING", length = 10)
    @Comment("학습 수준. 초급, 중급, 고급...")
    private String lvLearning;

    @Column(name = "ADDRESS1", length = 200)
    @Comment("주소1")
    private String address1;

    @Column(name = "ADDRESS2", length = 100)
    @Comment("주소2: 상세주소.")
    private String address2;

    @Column(name = "STUDENT_TEL", length = 15)
    @Comment("학생 연락처")
    private String studentTel;

    @Column(name = "PARENT_NM", length = 20)
    @Comment("학부모명")
    private String parentNm;

    @Column(name = "PARENT_TEL", length = 15, nullable = false)
    @Comment("학부모 연락처")
    private String parentTel;

    @Column(name = "MEMO", columnDefinition = "text")
    @Comment("특이사항. 간부 외 평강사도 기재 가능.")
    private String memo;

    // 학급 1 : 학생 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEARNING_CLASS_NO")
    @Comment("참조하는 학급 번호. 소속 학급.")
    private LearningClass learningClass;

    // 학생 증명사진 단방향 참조. uk 설정으로 1:1 관계 보장.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PIC_NO", unique = true)
    @Comment("참조하는 학생 증명사진 번호")
    private StudentPic studentPic;


    // update

    // res


}
