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
    // TODO: 수신인을 확인하려면 별도 연결 테이블 필요

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
    @Comment("내용")
    private String contents;

    @Column(name = "SCS_YN", length = 1)
    @Comment("발송실패여부")
    @ColumnDefault("'Y'")
    private Character successYn;

    // update

    // res


}
