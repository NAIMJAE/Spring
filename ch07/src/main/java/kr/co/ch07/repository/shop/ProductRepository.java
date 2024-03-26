package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.Customer;
import kr.co.ch07.entity.shop.Product;
import kr.co.ch07.repository.shop.custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {

}