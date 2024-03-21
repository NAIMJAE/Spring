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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

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
    
    // 게시글 작성 페이지 매핑
    @GetMapping("/article/write")
    public String write(@ModelAttribute("cate") String cate){
        log.info("/article/write - GET");
        return "/article/write";
    }

    // 게시글 출력 1개 (/article/view)
    @GetMapping("/article/view")
    public String view(String cate, int no, Model model){
        log.info("/article/view - GET");
        // 게시글 내용 조회
        ArticleDTO article = articleService.selectArticle(no);

        // 댓글 조회
        List<ArticleDTO> comments = articleService.selectComment(no);
        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        return "/article/view";
    }

    // 게시글 작성
    @PostMapping("/article/write")
    public String write(HttpServletRequest req, ArticleDTO articleDTO){
        log.info("/article/write - POST");

        String regip = req.getRemoteAddr();
        LocalDateTime rdate = LocalDateTime.now();
        articleDTO.setRegip(regip);
        articleDTO.setRdate(rdate);

        log.info(articleDTO.toString());

        articleService.insertArticle(articleDTO);

        return "redirect:/article/list";
    }
    
    // 게시글 수정 페이지 매핑
    @GetMapping("/article/modify")
    public String modify(int no, Model model){
        log.info("/article/modify - GET");
        ArticleDTO article = articleService.selectArticle(no);
        model.addAttribute("article", article);
        return "/article/modify";
    }
    
    // 게시글 수정
    @PostMapping("/article/modify")
    public String modify(HttpServletRequest req, ArticleDTO articleDTO){
        log.info("/article/modify - POST");

        String cate = articleDTO.getCate();
        String regip = req.getRemoteAddr();
        LocalDateTime rdate = LocalDateTime.now();
        articleDTO.setRegip(regip);
        articleDTO.setRdate(rdate);

        log.info(articleDTO.toString());

        articleService.updateArticle(articleDTO);

        return "redirect:/article/list";
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

    // 댓글 삭제
    @ResponseBody
    @GetMapping("/article/commentDelete/{no}")
    public ResponseEntity<?> commentDelete(@PathVariable("no") int no){
        log.info("/article/commentDelete - GET");
        return articleService.deleteComment(no);
    }

    // 댓글 수정
    @ResponseBody
    @GetMapping("/article/commentModify/{no}/{content}")
    public ResponseEntity<?> commentDelete(@PathVariable("no") int no, @PathVariable("content") String content){
        log.info("/article/commentModify - GET");
        return articleService.modifyComment(no, content);
    }
}
