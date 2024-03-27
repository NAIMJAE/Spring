package kr.co.sboard.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {

    // view 페이지 프로필 사진 조회
    public String selectProfile(String uid);

}
