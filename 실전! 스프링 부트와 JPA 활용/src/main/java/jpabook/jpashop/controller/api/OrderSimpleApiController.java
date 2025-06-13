package jpabook.jpashop.controller.api;

import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * V1. 엔티티 직접 노출
     * 권장하는 방식은 아니지만, 공부 목적으로 실습
     * 문제점
     * - JSON 직렬화 과정에서 연관관계로 인해 순환 참조 문제 발생 => JsonIgnore로 json 직렬화에 제외시키면 해결
     * - LAZY 로딩으로 인해 프록시 객체(bytebuddy)를 사용하는데, 이를 JSON 직렬화할 때 에러 발생
     * - Hibernate6Module 사용하면 해결 가능
     * - 강제로 로딩 시켜서 처리
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            String name = order.getMember().getName(); // 강제로 로딩
        }
        return orders;
    }

    /**
     * V2. DTO 반환
     * 문제점
     * - N + 1 문제 발생
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        return orderRepository.findAllByString(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    /**
     * V3. DTO 반환 - 패치조인
     * - 필요없는 필드까지 조회하지만 => 재사용성이 높다.
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        return orderRepository.findAllWithMemberDelivery().stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    /**
     * V4. DTO 반환 - Projection
     * 문제점
     * - 필요한 필드만 조회하기 때문에 성능은 좋지만, 재사용이 힘들다.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }

}
