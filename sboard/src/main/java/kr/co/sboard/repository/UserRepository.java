package kr.co.sboard.repository;

import kr.co.sboard.entity.User;
import kr.co.sboard.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
    public User findByNick(String nick);
    public User findByName(String name);

}
