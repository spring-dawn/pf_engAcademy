package portfolio.eams.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.dto.system.CommonCodeUsageDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert // 디폴트값 자동 세팅
@Entity
@Table(name = "FCT_CMN_CD_USAGE_T")
public class CommonCodeUsage {
    /*
    공통코드 시스템을 동적으로 사용하기 위한 기능성 매핑 테이블
    menu(page) 기준으로 공통코드 묶음을 매핑, 매핑된 공통코드를 캐싱하고 화면에서 쉽게 호출하는 용도
    호출키 기준으로 공통코드는 직계 하위 코드만 조회 가능. e.g) 1lv 매핑 시 2lv 까지만 호출.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMN_CD_USG_NO")
    private Long id;

    @Column(name = "CALL_KEY", length = 30, nullable = false)
    @Comment("코드 호출키. 해당 데이터map 의 key값 e.g)user_dropdown1")
    private String callKey;

    @Column(name = "RMRK", length = 100)
    @Comment("어떤 용도의 매핑인지 설명")
    private String rmrk;


    /*
    연관관계
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_NO", nullable = false)
    @Comment("참조하는 메뉴 번호")
    private Menu menu;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMN_CD_NO", nullable = false)
    @Comment("참조하는 공통코드 번호")
    private CommonCode commonCode;



    // res
    public CommonCodeUsageDto toRes(){
        return new CommonCodeUsageDto(id, menu.getId(), commonCode.getId(), callKey, rmrk);
    }

}
