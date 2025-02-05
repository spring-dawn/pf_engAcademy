package portfolio.eams.entity.academy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.entity.CommonEntity;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DIM_PARENT_MEETING_T")
public class ParentMeeting extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_MEETING_NO")
    private Long id;

    @Column(name = "MEETING_YMD", nullable = false)
    @Comment("상담일자")
    private LocalDate meetingDt;

    @Column(name = "MEETING_TYPE", length = 10, nullable = false)
    @Comment("상담 분류 코드. 민원, 성적, 태도, 기타 등")
    private String meetingType;

    @Column(name = "USER_ID", length = 20, nullable = false)
    @Comment("상담 강사(사용자 ID)")
    private String userId;

    @Column(name = "CONTENTS", columnDefinition = "text", nullable = false)
    @Comment("상담내용")
    private String contents;

    @Column(name = "SUMMARY", length = 200)
    @Comment("내용 요약")
    private String summary;


    // 학생 1 : 상담기록 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_NO")
    @Comment("학생 번호")
    private Student student;


    // update

    // res


}
