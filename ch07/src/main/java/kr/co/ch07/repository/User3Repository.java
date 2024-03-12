package kr.co.ch07.repository;

import kr.co.ch07.entity.User3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User3Repository extends JpaRepository<User3, String> {
}
