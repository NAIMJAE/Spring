package kr.co.sboard.entity;

import jakarta.persistence.*;
import kr.co.sboard.dto.FileDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;
    private int ano;
    private String oName;
    private String sName;

    @ColumnDefault("0")
    private int download;

    @CreationTimestamp
    private LocalDateTime rdate;

}
