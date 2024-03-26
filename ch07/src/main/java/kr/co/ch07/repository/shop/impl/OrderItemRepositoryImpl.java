package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.OrderItem;
import kr.co.ch07.entity.shop.QOrderItem;
import kr.co.ch07.repository.shop.custom.OrderItemRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QOrderItem qOrderItem = QOrderItem.orderItem;

    @Override
    public List<OrderItem> selectOrderItems(){
        return jpaQueryFactory.selectFrom(qOrderItem).fetch();
    }

    @Override
    public OrderItem selectOrderItem(int orderItemId){
        return jpaQueryFactory
                .selectFrom(qOrderItem)
                .where(qOrderItem.orderItemId.eq(orderItemId))
                .fetchOne();
    }
}