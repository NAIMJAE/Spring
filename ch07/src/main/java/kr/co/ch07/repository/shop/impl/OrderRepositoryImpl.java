package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.Order;
import kr.co.ch07.entity.shop.QOrder;
import kr.co.ch07.repository.shop.custom.OrderRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QOrder qOrder = QOrder.order;

    @Override
    public List<Order> selectOrders() {
        return jpaQueryFactory.selectFrom(qOrder).fetch();
    }

    @Override
    public Order selectOrder(int orderId) {
        return jpaQueryFactory
                .selectFrom(qOrder)
                .where(qOrder.orderId.eq(orderId))
                .fetchOne();
    }
}