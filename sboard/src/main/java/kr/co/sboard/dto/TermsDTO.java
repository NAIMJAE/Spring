package kr.co.sboard.dto;

import kr.co.sboard.entity.Terms;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TermsDTO {
    private String terms;
    private String privacy;
    private String sms;
    // toEntity
    public Terms toEntity(){
        return Terms.builder()
                .terms(terms).privacy(privacy).sms(sms).build();
    }
}