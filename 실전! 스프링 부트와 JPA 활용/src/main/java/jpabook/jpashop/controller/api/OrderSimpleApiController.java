package jpabook.jpashop.controller.api;

import jpabook.jpashop.entity.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * V1. 엔티티 직접 노출
     * 권장하는 방식은 아니지만, 공부 목적으로 실습
     * 문제점
     * - JSON 직렬화 과정에서 연관관계로 인해 순환 참조 문제 발생 => JsonIgnore로 json 직렬화에 제외시키면 해결
     * - LAZY 로딩으로 인해 프록시 객체(bytebuddy)를 사용하는데, 이를 JSON 직렬화할 때 에러 발생
     *   - Hibernate6Module 사용하면 해결 가능
     *   - 강제로 로딩 시켜서 처리
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            String name = order.getMember().getName(); // 강제로 로딩
        }
        return orders;
    }

}
