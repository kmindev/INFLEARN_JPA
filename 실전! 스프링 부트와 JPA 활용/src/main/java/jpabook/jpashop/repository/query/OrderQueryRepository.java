package jpabook.jpashop.repository.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(int offset, int limit) {
        List<OrderQueryDto> orders = findOrders(offset, limit);

        orders.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return orders;
    }

    private List<OrderQueryDto> findOrders(int offset, int limit) {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDto_optimization(int offset, int limit) {
        // Order 조회
        List<OrderQueryDto> orders = findOrders(offset, limit);

        // Order Id 컬렉션
        Set<Long> orderIds = orders.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toSet());

        // IN 쿼리
        List<OrderItemQueryDto> orderItems = findOrderItemsInOrderIds(orderIds);

        // Order - OrderItems 매칭 시켜주기
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return orders;
    }

    private List<OrderItemQueryDto> findOrderItemsInOrderIds(Set<Long> orderIds) {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
    }

}
