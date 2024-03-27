package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.config.AppInfo;
import kr.co.sboard.dto.*;
import kr.co.sboard.entity.Article;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.FileService;
import kr.co.sboard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    // 게시물 조회 (10개씩) (/article/view)
    @GetMapping("/article/list")
    public String list(Model model, String cate, PageRequestDTO pageRequestDTO){
        log.info("/article/list - GET");

        PageResponseDTO pageResponseDTO = null;

        if (pageRequestDTO.getKeyword() == null){
            // 일반 글 목록 조회
            pageResponseDTO = articleService.selectArticles(pageRequestDTO);
        }else {
            // 검색 글 목록 조회
            pageResponseDTO = articleService.searchArticles(pageRequestDTO);
        }

        model.addAttribute(pageResponseDTO);

        return "/article/list";
    }
    
    // 게시글 작성 페이지 매핑
    @GetMapping("/article/write")
    public String write(Model model, String cate, PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .build();

        model.addAttribute(pageResponseDTO);

        return "/article/write";
    }

    // 게시글 출력 1개 (/article/view)
    @GetMapping("/article/view")
    public String view(Model model, String cate, int no, PageRequestDTO pageRequestDTO){
        log.info("/article/view - GET");
        // 게시글 내용 조회 & 닉네임 조회 & 조회수 +1 // 조회수 반영 안되는듯
        ArticleDTO article = articleService.selectArticleAndHitPlus(no);

        // 댓글 조회
        List<ArticleDTO> comments = articleService.selectComment(no);

        // 프로필 사진 조회
        List<String> profiles = new ArrayList<>();
        for (ArticleDTO comment : comments) {
            String profile = articleService.selectProfile(comment.getWriter());
            profiles.add(profile);
        }

        // 페이징 정보
        PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .build();

        // 1. 현재 게시글 정보
        // 2. 댓글 정보
        // 3. 게시글, 댓글 닉네임
        // 4. 프로필 사진

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        model.addAttribute("profiles", profiles);
        model.addAttribute(pageResponseDTO);
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
        int no = articleService.insertArticle(articleDTO);

        return "redirect:/article/view?no=" + no;
    }
    
    // 게시글 수정 페이지 매핑
    @GetMapping("/article/modify")
    public String modify(int no, Model model){
        log.info("/article/modify - GET");
        ArticleDTO article = articleService.selectArticleAndHitPlus(no); // 수정해야함 hit +1 없는걸로
        model.addAttribute("article", article);
        return "/article/modify";
    }
    
    // 게시글 수정
    @PostMapping("/article/modify")
    public String modify(HttpServletRequest req, ArticleDTO articleDTO){
        log.info("게시글 수정 controller1 시작");
        log.info("게시글 수정 controller2 articleDTO : " + articleDTO.toString());

        String cate = articleDTO.getCate();
        String regip = req.getRemoteAddr();
        LocalDateTime rdate = LocalDateTime.now();
        articleDTO.setRegip(regip);
        articleDTO.setRdate(rdate);

        log.info("게시글 수정 controller3 articleDTO : " + articleDTO.toString());

        int no = articleService.updateArticle(articleDTO);

        log.info("게시글 수정 controller4 끝");
        return "redirect:/article/view?no=" + no;
    }

    // 게시글 삭제
    @GetMapping("/article/delete")
    public String delete(int no, String cate){
        log.info("/article/delete - GET");
        articleService.deleteArticle(no);

        return "redirect:/article/list?cate=" + cate;
    }

    // 댓글 작성 ////nick
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

    // 댓글 수정 //// nick
    @ResponseBody
    @GetMapping("/article/commentModify/{no}/{content}")
    public ResponseEntity<?> commentDelete(@PathVariable("no") int no, @PathVariable("content") String content){
        log.info("/article/commentModify - GET");
        return articleService.modifyComment(no, content);
    }
}
