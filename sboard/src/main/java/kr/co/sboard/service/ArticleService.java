package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    // 별도의 빈 등록 필요
    private final ModelMapper modelMapper;

    // 게시글 등록
    public void insertArticle(ArticleDTO articleDTO){
        // etnity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        articleRepository.save(article);
    }

    public ArticleDTO selectArticle(int no){
        return null;
    }

    // x
    public List<ArticleDTO> selectArticles(){
        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles){
            articleDTOs.add(modelMapper.map(articles, ArticleDTO.class));
        }
        return articleDTOs;
    }

    public void updateArticle(ArticleDTO articleDTO){

    }

    public void deleteArticle(int no){

    }

    // 게시판별 게시글 조회
    public List<ArticleDTO> selectArticleForCate(String cate, int currentPg){
        //List<Article> articles = articleRepository.findByCateAndParentOrderByNoDesc(cate, 0);
        log.info("startNum : "+currentPg);
        // 페이지 번호, 페이지 크기, 정렬 방식을 지정하여 Pageable 객체 생성
        Pageable pageable = PageRequest.of((currentPg-1), 10);

        // Repository 메서드에 Pageable 객체 전달하여 페이징 처리된 결과 가져오기
        Page<Article> articles = articleRepository.findByCateAndParentOrderByNoDesc(cate, 0, pageable);


        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles){
            articleDTOs.add(modelMapper.map(article, ArticleDTO.class));
            log.info(modelMapper.map(article, ArticleDTO.class).toString());
        }
        return articleDTOs;
    }
    // 게시글 개수 조회
    public int selectArticleCount(String cate){
        return articleRepository.countByCateAndParent(cate, 0);
    }
}
