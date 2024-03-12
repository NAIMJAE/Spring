package kr.co.ch07.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="board_user")
public class User {
    @Id
    private String uid;
    private String name;
    private String hp;
    @CreationTimestamp
    private LocalDateTime regDate;
}
