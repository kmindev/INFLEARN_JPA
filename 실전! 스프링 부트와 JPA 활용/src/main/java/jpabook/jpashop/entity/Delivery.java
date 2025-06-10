package jpabook.jpashop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 1대1 관계에서는 연관관계 주인을 많이 사용하는 쪽에 두는 것이 좋음.
    private Order order;

    @Embedded private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
