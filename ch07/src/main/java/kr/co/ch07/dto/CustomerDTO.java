package kr.co.ch07.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private String custId;
    private String name;
    private int age;
    private String hp;
    private String addr;
    private LocalDateTime regDate;


    private long orderCount;
}