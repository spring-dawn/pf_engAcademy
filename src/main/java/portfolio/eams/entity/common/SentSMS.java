package portfolio.eams.entity.common;

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
@Table(name = "DIM_SMS_T")
public class SentSMS extends CommonEntity {
    // 웹 발송 sms 기록.
    // 발송 대상이 사용자가 아닌 학생(학부모)이므로 CC 등의 참조 기능 없이 수신인 정보를 일반 문자열로 처리.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SMS_NO")
    private Long id;

    @Column(name = "USER_ID", length = 20)
    @Comment("발신인(강사 등 직원 ID)")
    private String userId;

    @Column(name = "NUM_TO")
    @Comment("수신인원")
    private int numTo;

    @Column(name = "CONTENTS", columnDefinition = "text")
    @Comment("내용. SMS 글자제한수 참고.")
    private String contents;

    @Column(name = "SCS_YN", length = 1)
    @Comment("발송 성공 여부. 발송 실패 기준 별도 참고.")
    @ColumnDefault("'Y'")
    private Character successYn;

    // update

    // res


}
