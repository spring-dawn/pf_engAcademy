package portfolio.eams.entity.system;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    전역에서 쓰일 공통코드 분류 체계
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_NO")
    private Long id;

    @Column(name = "CD", unique = true, nullable = false, length = 20)
    @Comment("영문 대소문자, 숫자를 조합한 식별자. 주로 DB 저장 형태")
    private String cd;

    @Column(name = "CD_VAL", nullable = false, length = 100)
    @Comment("코드값, 화면에 출력되는 형태.")
    private String cdVal;

    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부. Y: 사용, N: 미사용")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "LAST_YN", length = 1)
    @ColumnDefault("'N'")
    @Comment("최하단여부. 3단계보다 깊은 코드 생성 제한.")
    private Character lastYn;

    @Column(name = "SYS_YN", length = 1)
    @ColumnDefault("'N'")
    @Comment("시스템 관여 여부. 시스템 코드인 경우 개발자만 편집 가능. Y: 시스템, N: 일반")
    private Character sysYn;

//    @Column(name = "CD_DIGITS")
//    @Comment("코드 자릿수 지정 e.g.) 학생 교육단계: 3(자리)")
//    @ColumnDefault("'1'")
//    private byte digits;

    @Column(name = "MEMO", length = 200)
    @Comment("기타")
    private String memo;

    // 트리 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CD")
    @Comment("참조하는 상위 코드")
    private Code parent;

    @JsonIgnore // 순환참조 주의
    @OneToMany(mappedBy = "parent")
    private List<Code> children = new ArrayList<>();


    // update
    public void update(CodeDto.Req req) {
        // 변경 가능 데이터: cd, cdVal, memo, useYn
        this.cd = req.getCd();
        this.cdVal = req.getCdVal();
        this.memo = req.getMemo();
        this.useYn = req.getUseYn();
    }


    // res
    public CodeDto toRes() {
        return CodeDto.builder()
                .id(id)
                .cd(cd)
                .cdVal(cdVal)
                .memo(memo)
                .useYn(useYn)
                .sysYn(sysYn)
                .lastYn(lastYn)
                .parentId(parent == null ? null : parent.getId())
                .children(children == null? null : children.stream().toList())
                .build();
    }


}
