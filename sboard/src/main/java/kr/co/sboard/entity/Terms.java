package kr.co.sboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.sboard.dto.TermsDTO;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="terms")
public class Terms {
    @Id
    private String terms;
    private String privacy;
    private String sms;
    // toDTO
    public TermsDTO toDTO(){
        return TermsDTO.builder()
                .terms(terms).privacy(privacy).sms(sms).build();
    }
}
