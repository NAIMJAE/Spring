package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void selectOrderItems(){
        List<OrderItem> orderItems = orderItemRepository.selectOrderItems();
        log.info("orderItems : " + orderItems);
    }

    @Test
    public void selectOrderItem(){
        OrderItem orderItem = orderItemRepository.selectOrderItem(1);
        log.info("orderItem : " + orderItem);
    }

}