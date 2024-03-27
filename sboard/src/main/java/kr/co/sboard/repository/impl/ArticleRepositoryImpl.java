package kr.co.sboard.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.QArticle;
import kr.co.sboard.entity.QUser;
import kr.co.sboard.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QArticle qArticle = QArticle.article;
    private QUser qUser = QUser.user;

    @Override
    public Page<Tuple> selectArticles(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String cate = pageRequestDTO.getCate();
        log.info("cate : " + cate);

        // 부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Tuple> results = jpaQueryFactory
                                        .select(qArticle, qUser.nick)
                                        .from(qArticle)
                                        .where(qArticle.cate.eq(cate).and(qArticle.parent.eq(0)))
                                        .join(qUser)
                                        .on(qArticle.writer.eq(qUser.uid))
                                        .orderBy(qArticle.rdate.desc())
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .fetchResults();
        //
        List<Tuple> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Article> searchArticles(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String cate = pageRequestDTO.getCate();
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        log.info("cate : " + cate + " | type : " + type + " | keyword : " + keyword);

        // 검색 종류에 따른 where 표현식 생성
        BooleanExpression expression = null;
        if (type.equals("title")){
            expression = qArticle.cate.eq(cate).and(qArticle.title.contains(keyword));

        }else if (type.equals("content")){
            expression = qArticle.cate.eq(cate).and(qArticle.content.contains(keyword));

        }else if (type.equals("titleContent")){
            BooleanExpression titleContains = qArticle.title.contains(keyword);
            BooleanExpression contentContains = qArticle.content.contains(keyword);
            expression = qArticle.cate.eq(cate).and(titleContains.or(contentContains));

        }else if (type.equals("writer")){

            expression = qArticle.cate.eq(cate).and(qArticle.nick.contains(keyword));

        }
        // select * from `article` where `cate` = [cate] and `[type]` = [keyword]

        // 부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Article> results = jpaQueryFactory
                                            .selectFrom(qArticle)
                                            .where(expression)
                                            .orderBy(qArticle.rdate.desc())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetchResults();
        // select * from `article` where `cate` = [cate] and `[type]` = [keyword] limit (0 , 10)

        //
        List<Article> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Article selectArticleAndHitPlus(int no) {
        // select * from article where no = ?
        Article article = jpaQueryFactory
                            .selectFrom(qArticle)
                            .where(qArticle.no.eq(no))
                            .fetchOne();
        // update article set hit =+ 1 where no = ?
        jpaQueryFactory
                .update(qArticle)
                .set(qArticle.hit, qArticle.hit.add(1))
                .where(qArticle.no.eq(no));

        return article;
    }
    @Override
    public List<Article> selectComment(int parent){
        // select * from article where parent = ?
        List<Article> article = jpaQueryFactory
                            .selectFrom(qArticle)
                            .where(qArticle.parent.eq(parent))
                            .fetch();
        return article;
    }

}
