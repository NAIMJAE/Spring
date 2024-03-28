package kr.co.sboard.repository;

import kr.co.sboard.entity.Config;
import kr.co.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, String> {
}
