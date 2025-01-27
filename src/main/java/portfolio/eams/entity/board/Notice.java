package portfolio.eams.entity.board;

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
@Table(name = "DIM_NOTICE_T")
public class Notice extends CommonEntity {
    // 공지사항. 등록 시 웹소켓으로 사용자 전체에 알림 발송.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_NO")
    private Long id;

    @Column(name = "SUBJECT", length = 100, nullable = false)
    @Comment("제목")
    private String subject;

    @Column(name = "CONTENTS", columnDefinition = "text", nullable = false)
    @Comment("내용")
    private String contents;

    @Column(name = "HIT")
    @Comment("조회수")
    private int hit;

    @Column(name = "TMP_YN", length = 1, nullable = false)
    @Comment("임시저장 여부. 임시저장인 경우 노출되지 않음.")
    @ColumnDefault("'N'")
    private Character tmpYn;

    @Column(name = "IMPORTANCE")
    @Comment("중요도. 1: 낮음, 2: 보통, 3: 중요. 별 3개 기준.")
    private int importance;


    // update

    // res


}
