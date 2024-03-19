package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void insertArticle(ArticleDTO articleDTO){
        // etnity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        articleRepository.save(article);
    }

    public ArticleDTO selectArticle(int no){
        return null;
    }

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
    public List<ArticleDTO> selectArticleForCate(String cate){
        List<Article> articles = articleRepository.findByCateAndParentOrderByNoDesc(cate, 0);

        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles){
            articleDTOs.add(modelMapper.map(article, ArticleDTO.class));
        }
        return articleDTOs;
    }
}
