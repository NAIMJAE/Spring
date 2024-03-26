package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertProduct(){

        Product product = Product.builder()
                .productName("컴퓨터")
                .stock(50)
                .price(1300000)
                .build();


        productRepository.save(product);

    }

}