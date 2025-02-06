package portfolio.eams.entity.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_SUPPLY_ORDER_DETAIL_T")
public class SupplyOrderDetail {
    // 비품 발주서의 상세 내역. 구매 품목 데이터행.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLY_ORDER_DETAIL_NO")
    private Long id;

    @Column(name = "SUPPLY_NM", length = 100)
    @Comment("비품명. 코드 참고.")
    private String supplyNm;

    @Column(name = "ORDER_QTY")
    @Comment("발주 수량")
    private int orderQty;

    @Column(name = "PRICE")
    @Comment("개당 가격(원)")
    private int price;

    //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLY_ORDER_NO")
    @Comment("참조하는 발주서 번호")
    private SupplyOrder supplyOrder;


    // update


}
