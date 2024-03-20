package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    /*
        @ModelAttribute("cate")
         - modelAttribute("cate", cate)와 동일
    */
    // 게시물 조회 (10개씩)
    @GetMapping("/article/list")
    public String list(Model model, PageRequestDTO pageRequestDTO){
        log.info("/article/list - GET");
        log.info(pageRequestDTO.toString());
        // 게시판 종류별 게시물 10개씩 조회
        PageResponseDTO pageResponseDTO = articleService.selectArticleForCate(pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/article/list";
    }

    @GetMapping("/article/write")
    public String write(@ModelAttribute("cate") String cate){
        log.info("/article/write - GET");
        return "/article/write";
    }

    // 게시글 작성
    @PostMapping("/article/write")
    public String write(HttpServletRequest req, ArticleDTO articleDTO){
        log.info("/article/write - POST");
        /*
            글작성을 테스트할 때는 로그인해야하기 때문에
            SecurityConfig 인가 설정 수정할 것
        */
        String regip = req.getRemoteAddr();
        articleDTO.setRegip(regip);

        log.info(articleDTO.toString());

        articleService.insertArticle(articleDTO);

        return "redirect:/article/list";
    }
    // 게시글 출력 1개 (/article/view)
    @GetMapping("/article/view")
    public String view(String cate, int no, Model model){
        log.info("/article/view - GET");
        // 게시글 내용 조회
        ArticleDTO article = articleService.selectArticle(no);
        log.info("article" + article.toString());
        
        // 게시글 조회수 카운트 +1
        
        
        // 댓글 조회

        
        model.addAttribute("article", article);
        return "/article/view";
    }
    
    // 파일 다운로드
    @GetMapping("/article/fileDownload")
    public ResponseEntity<?> fileDownload(int fno){
        return fileService.fileDownload(fno);
    }
    
    
    // 댓글 작성
    @PostMapping("/article/comment")
    public ResponseEntity<?> comment(HttpServletRequest req,@RequestBody ArticleDTO articleDTO){
        log.info("/article/comment - POST");

        String regip = req.getRemoteAddr();
        articleDTO.setRegip(regip);

        log.info(articleDTO.toString());

        return articleService.insertComment(articleDTO);
    }
}
