package kr.co.ch07.repository;

import kr.co.ch07.entity.User1;
import kr.co.ch07.entity.User5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User5Repository extends JpaRepository<User5, Integer> {


}