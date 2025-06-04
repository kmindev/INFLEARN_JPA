package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 연관관계 주인 설정(Order.member로 주인 설정)
    private List<Order> orders = new ArrayList<>();

}
