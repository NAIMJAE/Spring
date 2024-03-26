package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.Customer;
import kr.co.ch07.entity.shop.Order;
import kr.co.ch07.repository.shop.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {
}