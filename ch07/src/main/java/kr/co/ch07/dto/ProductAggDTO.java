package kr.co.ch07.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAggDTO {
    private int priceSum;
    private double priceAvg;
    private int priceMax;
    private int priceMin;
}