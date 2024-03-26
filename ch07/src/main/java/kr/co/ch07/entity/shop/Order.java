package kr.co.ch07.entity.shop;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "shop_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "orderer")
    private Customer customer;
    private int orderStatus;
    private int orderPrice;

    @CreationTimestamp
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;


}