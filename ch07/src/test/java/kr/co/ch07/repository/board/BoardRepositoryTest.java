package kr.co.ch07.repository.board;

import jakarta.transaction.Transactional;
import kr.co.ch07.entity.Article;
import kr.co.ch07.entity.Comment;
import kr.co.ch07.entity.File;
import kr.co.ch07.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class BoardRepositoryTest {

    @Autowired private ArticleRepository articleRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private FileRepository fileRepository;
    @Autowired private UserRepository userRepository;


    public void insertUser(){
        User user = User.builder()
                .uid("A920").name("잠보고").hp("041-1356-3551").build();
        userRepository.save(user);
    }


    public void insertArticle(){
        // user 칼럼 참조용 User Entity 생성
        User user = User.builder()
                .uid("A920").build();

        Article article = Article.builder()
                .title("테스트3 제목")
                .content("테스트3 내용")
                .user(user)
                .build();
        articleRepository.save(article);
    }

    public void insertComment(){
        // User Entity, Article Entity 생성
        User user = User.builder()
                .uid("A910").build();
        Article article = Article.builder()
                .no(3).build();

        Comment comment = Comment.builder()
                .content("테스트6 댓글")
                .user(user)
                .article(article)
                .build();

        commentRepository.save(comment);
    }

    public void insertFile(){
        // Article Entity 생성
        Article article = Article.builder()
                .no(3).build();

        File file = File.builder()
                .sName("원래파일명.txt")
                .oName("YJSDGEWFGEG.txt")
                .article(article).build();
        fileRepository.save(file);
    }
    /*
        연관 관계로 설정된 Entity를 조회할 때 하나 이상의 SELECT가 실행(findAll()..)되기 때문에
        Transactional 선언으로 한 번의 실행으로 처리해야 no session 에러 방지
     */
    @Test
    @Transactional
    public void selectArticles(){
        List<Article> articles = articleRepository.findAll();

        for(Article article : articles){
            log.info(article.toString());
        }
    }
}