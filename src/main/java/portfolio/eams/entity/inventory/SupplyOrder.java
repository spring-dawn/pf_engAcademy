package portfolio.eams.entity.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import portfolio.eams.entity.CommonEntity;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "DIM_SUPPLY_ORDER_T")
public class SupplyOrder extends CommonEntity {

    @Id
    @Column(name = "SUPPLY_ORDER_NO", length = 20)
    private String id; // 문서번호 규칙 필요

    @Column(name = "APPROVAL", nullable = false)
    @Comment("결재상태. 0: 결재대기, 1: 진행, 2: 취소, 3:완료")
    @ColumnDefault("'0'")
    private int approval;

    @Column(name = "APPROVER", length = 20)
    @Comment("결재자(사용자 ID)")
    private String approver;

    @Column(name = "ORDER_YMD")
    @Comment("발주일, 주문 예상일")
    private LocalDate orderDt;

    @Column(name = "ARRIVAL_YMD")
    @Comment("입고일, 배송 예상일")
    private LocalDate arrivalDt;

    @Column(name = "MEMO", length = 200)
    @Comment("기타")
    private String memo;

    // update

    // res
}
