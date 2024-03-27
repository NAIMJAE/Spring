package kr.co.sboard.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    // list 페이지 게시글 목록 조회
    public Page<Article> searchArticles(PageRequestDTO pageRequestDTO, Pageable pageable);
    public Page<Tuple> selectArticles(PageRequestDTO pageRequestDTO, Pageable pageable);

    // veiw 페이지 게시글 조회
    public Article selectArticleAndHitPlus(int no);
    // veiw 페이지 댓글 조회
    public List<Article> selectComment(int parent);

}
