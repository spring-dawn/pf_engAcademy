package portfolio.eams.entity.system;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.dto.system.CommonCodeDto;
import portfolio.eams.entity.BasicEntityColumn;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert // 디폴트값 자동 세팅
@Entity
@Table(name = "SYS_CMN_CD_T")
public class CommonCode extends BasicEntityColumn {
    /*
    전역에서 쓰일 공통코드 분류 체계
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMN_CD_NO")
    private Long id;

    @Column(name = "CMN_CD", unique = true, nullable = false, length = 30)
    @Comment("영문 대소문자, 숫자 등을 조합한 고유 식별자")
    private String cd;

    @Column(name = "CMN_CD_VAL", nullable = false, length = 200)
    @Comment("실제 의미")
    private String cdVal;

    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부. Y: 사용, N: 미사용")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "DEPTH", nullable = false)
    @Comment("깊이 최대 3단계까지. 가능한 한 2단계까지만 권장")
    private Integer depth;

    @Column(name = "SORT_SEQ", nullable = false)
    @Comment("정렬순서. 같은 깊이(depth)에서 숫자가 작을수록 우선")
    @ColumnDefault("'0'")
    private Integer sortSeq;

    @Column(name = "MEMO", length = 200)
    @Comment("비고")
    private String memo;


    // 트리 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CMN_CD")
    @Comment("참조하는 상위 코드")
    private CommonCode parent;

    @JsonIgnore // 순환참조 주의
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommonCode> children = new ArrayList<>();


    // update
    public void update(CommonCodeDto.Req req) {
        // 변경 가능 데이터: cd, cdVal, memo, useYn
        cdVal = req.getCdVal();
        memo = req.getMemo();
        useYn = req.getUseYn();
        sortSeq = req.getSortSeq();
    }


    // res
    public CommonCodeDto toRes() {
        return CommonCodeDto.builder()
                .id(id)
                .cd(cd)
                .cdVal(cdVal)
                .depth(depth)
                .sortSeq(sortSeq)
                .memo(memo)
                .useYn(useYn)
                .parentId(parent == null ? null : parent.getId())
                .children(children == null? null : children.stream().toList())
                .build();
    }


}
