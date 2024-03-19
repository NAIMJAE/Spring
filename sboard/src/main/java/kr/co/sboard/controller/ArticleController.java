package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

    /*
        @ModelAttribute("cate")
         - modelAttribute("cate", cate)와 동일
    */
    // 게시물 조회 (10개씩)
    @GetMapping("/article/list")
    public String list(@ModelAttribute("cate") String cate,
                       @ModelAttribute("pg") String pg, Model model){
        log.info("/article/list - GET");
        // 현재 페이지 번호
        log.info("pg : "+pg);
        int currentPg = 1;
        if(pg != null && !pg.isEmpty()){
            currentPg = Integer.parseInt(pg);
        }
        // DB 조회에서 사용하는 시작 넘버
        int startNum = (currentPg-1)*10;
        // 총 페이지 수
        int totalCount = articleService.selectArticleCount(cate);
        int lastPageNum = (int) Math.ceil((double)totalCount/10);
        log.info("totalCount : "+totalCount);
        log.info("lastPageNum : "+lastPageNum);
        // Pagination 시작, 끝
        int startPagination = (((int)Math.ceil((double)currentPg/10)-1)*10)+1;
        int endPagination = ((int)Math.ceil((double)currentPg/10)*10);
        if (endPagination > lastPageNum){
            endPagination = lastPageNum;
        }
        // 게시글 목록 번호
        int articleStartNum = totalCount - startNum;
        // 게시판 종류별 게시물 10개씩 조회
        List<ArticleDTO> articles = articleService.selectArticleForCate(cate, currentPg);
        // 모델 전송
        model.addAttribute("currentPg", currentPg);
        model.addAttribute("startPagination", startPagination);
        model.addAttribute("endPagination", endPagination);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("articleStartNum", articleStartNum);
        model.addAttribute("cate", cate);
        model.addAttribute("articles", articles);

        return "/article/list";
    }



    @GetMapping("/article/write")
    public String write(@ModelAttribute("cate") String cate){
        log.info("/article/write - GET");
        return "/article/write";
    }

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
}
