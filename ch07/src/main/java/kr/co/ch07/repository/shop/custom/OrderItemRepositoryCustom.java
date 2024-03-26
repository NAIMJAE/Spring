package kr.co.ch07.repository.shop.custom;

import kr.co.ch07.entity.shop.OrderItem;

import java.util.List;

public interface OrderItemRepositoryCustom {
    public List<OrderItem> selectOrderItems();
    public OrderItem selectOrderItem(int orderItemId);
}