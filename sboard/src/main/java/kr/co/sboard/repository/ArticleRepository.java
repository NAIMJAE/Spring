package kr.co.sboard.repository;

import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCateAndParentOrderByNoDesc(String cate, int parent);

    int countByCateAndParent(String cate, int parent);

    Page<Article> findByCateAndParentOrderByNoDesc(String cate, int parent, Pageable pageable);
}
