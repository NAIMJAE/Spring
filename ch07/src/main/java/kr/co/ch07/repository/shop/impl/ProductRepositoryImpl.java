package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.Product;
import kr.co.ch07.entity.shop.QProduct;
import kr.co.ch07.repository.shop.custom.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QProduct qProduct = QProduct.product;

    public List<Product> selectProducts(){
        return jpaQueryFactory.selectFrom(qProduct).fetch();
    }
    public Product selectProduct(int productId){
        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(qProduct.productId.eq(productId))
                .fetchOne();
    }
}