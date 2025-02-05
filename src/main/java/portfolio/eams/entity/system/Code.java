package portfolio.eams.entity.system;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.dto.system.CodeDto;
import portfolio.eams.entity.CommonEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert // 디폴트값 자동 세팅
@Entity
@Table(name = "SYS_CD_T")
public class Code extends CommonEntity {
    /*
    전역에서 쓰일 마스터코드 분류 체계 저장
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_NO")
    private Long id;

    @Column(name = "CD_NM", unique = true, nullable = false, length = 10)
    @Comment("코드(명). DB 저장되는 형태.")
    private String cdNm;

    @Column(name = "CD_VAL", nullable = false, length = 20)
    @Comment("코드값. 화면에 출력되는 형태.")
    private String cdVal;

    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부. Y: 사용, N: 미사용")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "LAST_YN", length = 1)
    @Comment("최하단여부. 3단계보다 깊은 코드 생성 제한.")
    private Character lastYn;

    @Column(name = "CD_DIGITS")
    @Comment("코드 자릿수 지정. 해당 코드를 사용할 도메인이 몇 자리인지 기입. e.g.) domainType: 3")
    @ColumnDefault("'1'")
    private byte digits;

    @Column(name = "MEMO", length = 50)
    @Comment("기타 비고")
    private String memo;

    // 트리 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CD")
    @Comment("참조하는 상위 코드")
    private Code parent;

    @OneToMany(mappedBy = "parent")
    private List<Code> children = new ArrayList<>();


    // TODO: update


    // res
    public CodeDto toRes() {
        return CodeDto.builder()
                .build();
    }


}
