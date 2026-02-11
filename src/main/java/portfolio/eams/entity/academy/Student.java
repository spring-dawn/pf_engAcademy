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
@Table(name = "DIM_STDNT_T")
public class Student extends BasicEntityColumn {
    // 수강생 관리 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STDNT_NO")
    private Long id;

    @Column(name = "STDNT_NM", length = 100, nullable = false)
    @Comment("수강생명")
    private String studentNm;

    @Column(name = "STDNT_TEL", length = 20)
    @Comment("수강생 연락처")
    private String tel;

    @Column(name = "BIRTHDATE")
    @Comment("생년월일")
    private LocalDate birthdate;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    @Comment("현재 등록여부")
    private Character useYn;

    @Column(name = "SCHOOL_NM", length = 200)
    @Comment("재학 중인 학교명")
    private String schoolNm;

    @Column(name = "NEXT_OF_KIN_TEL", length = 20 , nullable = false, unique = true)
    @Comment("보호자 연락처")
    private String parentTel;

    @Column(name = "NEXT_OF_KIN_NM", length = 20)
    @Comment("보호자명")
    private String parentNm;

    @Column(name = "ADDR1", length = 250)
    @Comment("주소1")
    private String address1;

    @Column(name = "ADDR2", length = 150)
    @Comment("상세주소")
    private String address2;

    @Column(name = "RMRK", columnDefinition = "text")
    @Comment("비고(특이사항)")
    private String rmrk;


    // 연관관계. 학급 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLSRM_NO")
    @Comment("소속 학급 fk")
    private Classroom classroom;

    // TODO update


    // TODO res

}
