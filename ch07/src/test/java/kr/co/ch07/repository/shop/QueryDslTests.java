package kr.co.ch07.repository.shop;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import kr.co.ch07.dto.CustomerDTO;
import kr.co.ch07.dto.ProductAggDTO;
import kr.co.ch07.entity.shop.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class QueryDslTests {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QCustomer qCustomer = QCustomer.customer;
    QProduct qProduct = QProduct.product;
    QOrder qOrder = QOrder.order;

    @Test
    public void test01(){

        List<Product> products = jpaQueryFactory
                .select(qProduct)
                .from(qProduct)
                .fetch();

        log.info("test01 : " + products);

    }

    @Test
    public void test02(){
        // 선택조회 - select custId, name, age from customer;
        List<Customer> customers = jpaQueryFactory
                .select(
                        Projections.fields(
                                Customer.class,
                                qCustomer.custId,
                                qCustomer.name,
                                qCustomer.age
                        )
                )
                .from(qCustomer)
                .fetch();

        log.info("test02 : " + customers);

    }

    @Test
    public void test03(){

        // select * from customer where name='김유신'
        List<Customer> customers1 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.name.eq("김유신")).fetch();
        log.info("customers1 : " + customers1);

        // select * from customer where name!='김유신'
        List<Customer> customers2 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.name.ne("김유신")).fetch();
        log.info("customers2 : " + customers2);
    }

    @Test
    public void test04(){
        List<Customer> result1 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.gt(30)).fetch();     // ~ where age > 30
        List<Customer> result2 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.goe(30)).fetch();    // ~ where age >= 30
        List<Customer> result3 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.lt(30)).fetch();     // ~ where age < 30
        List<Customer> result4 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.loe(30)).fetch();    // ~ where age <= 30

        log.info("result1 : " + result1);
        log.info("result2 : " + result2);
        log.info("result3 : " + result3);
        log.info("result4 : " + result4);
    }

    @Test
    public void test05(){
        // select * from customer where age in (21, 31, 41)
        List<Customer> customers = jpaQueryFactory.selectFrom(qCustomer)
                .where(qCustomer.age.in(21, 31, 41))
                .fetch();

        log.info("test05 : " + customers);
    }

    @Test
    public void test06(){
        // select ~ where name like '%신'
        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(qCustomer.name.like("%신"))
                .fetch();

        log.info("test06 : " + customers);
    }

    @Test
    public void test07(){
        // select ~ where stock > 0 order by price desc;
        List<Product> products = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.stock.gt(0))
                .orderBy(qProduct.price.desc())
                .fetch();

        log.info("test07 : " + products);
    }

    @Test
    public void test08(){
        // select ~ where stock > 0 order by price asc limit 0, 3
        List<Product> products = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.stock.gt(0))
                .orderBy(qProduct.price.asc())
                .offset(0)
                .limit(3)
                .fetch();

        log.info("test08 : " + products);
    }

    @Test
    public void test09(){
        // select sum(`price`) as `priceSum`, avg(`price`) as `priceAvg` ...
        ProductAggDTO productAggDTO = jpaQueryFactory
                .select(
                        Projections.fields(
                                ProductAggDTO.class,
                                qProduct.price.sum().as("priceSum"),
                                qProduct.price.avg().as("priceAvg"),
                                qProduct.price.max().as("priceMax"),
                                qProduct.price.min().as("priceMin")
                        )
                )
                .from(qProduct)
                .fetchOne();

        log.info("test09 : " + productAggDTO);
    }

    @Test
    public void test10(){
        // select ~ where orderStatus = 1 group by `orderer` having custId >= 2;
        List<CustomerDTO> orders = jpaQueryFactory
                .select(
                        Projections.fields(
                                CustomerDTO.class,
                                qOrder.customer.custId,
                                qOrder.customer.name,
                                qOrder.customer.custId.count().as("orderCount")
                        )
                )
                .from(qOrder)
                .where(qOrder.orderStatus.eq(1))
                .groupBy(qOrder.customer.custId)
                .having(qOrder.customer.custId.count().goe(2))
                .fetch();

        log.info("test10 : " + orders);
    }

    @Test
    @Transactional
    public void test11(){
        List<Tuple> orders = jpaQueryFactory
                .select(qOrder, qCustomer)
                .from(qOrder)
                .join(qCustomer)
                .on(qOrder.customer.eq(qCustomer))
                .fetch();

        log.info("test11 : " + orders);
    }

    @Test
    public void test12(){

        String name = null;
        Integer age = 21;

        // 동적 쿼리 생성 builder
        BooleanBuilder builder = new BooleanBuilder();

        if(name != null){
            builder.and(qCustomer.name.eq(name));
        }

        if(age != null){
            builder.and(qCustomer.age.goe(age));
        }

        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(builder)
                .fetch();

        log.info("test12 : " + customers);
    }

    @Test
    public void test13(){

        String keyword = "유신";

        // 동적쿼리 생성을 위한 BooleanExpression
        BooleanExpression expression = qCustomer
                .name.containsIgnoreCase(keyword)
                .or(qCustomer.addr.containsIgnoreCase(keyword));

        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(expression)
                .fetch();

        log.info("test13 : " + customers);
    }


}