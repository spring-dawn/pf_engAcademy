package portfolio.eams.entity.inventory;

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
@DynamicInsert
@Entity
@Table(name = "DIM_SUPPLY_INFO_T")
public class SupplyInfo extends CommonEntity {
    // 원내에서 사용하는 비품 정보 등록

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLY_INFO_NO")
    private Long id;

    @Column(name = "SUPPLY_TYPE", length = 10, nullable = false)
    @Comment("비품 분류 코드. 교재, 일회용, 문구류, 사무용품 등...")
    private String supplyType;

    @Column(name = "SUPPLY_NM", length = 100, unique = true)
    @Comment("비품명")
    private String supplyNm;

    @Column(name = "VENDOR_NM", length = 100)
    @Comment("제조사명 or 공급처 정보")
    private String maker;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment("사용여부")
    @ColumnDefault("'Y'")
    private Character useYn;

    @Column(name = "VENDOR_TEL", length = 20)
    @Comment("거래처 전화번호")
    private String tel;

    @Column(name = "VENDOR_EML", length = 50, unique = true)
    @Comment("거래처 문의메일")
    private String email;

    @Column(name = "MEMO", length = 200)
    @Comment("특이사항")
    private String memo;


    // update

    // res


}
