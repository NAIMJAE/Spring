package kr.co.sboard.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.sboard.entity.QUser;
import kr.co.sboard.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QUser qUser = QUser.user;

    // view 페이지 프로필 사진 조회
    public String selectProfile(String uid){
        // select `profile` from user where uid = ?
        String profile = jpaQueryFactory
                            .select(qUser.profile)
                            .from(qUser)
                            .where(qUser.uid.eq(uid))
                            .fetchFirst();
        return profile;
    }

}
