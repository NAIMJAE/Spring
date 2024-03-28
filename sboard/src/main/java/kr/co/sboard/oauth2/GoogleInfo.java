package kr.co.sboard.oauth2;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GoogleInfo {

    private Map<String, Object> attributes;

    public String getId(){
        return attributes.get("id").toString();
    }

    public String getProvider(){
        return "google";
    }

    public String getEmail(){
        Map<?, ?> map = (Map<?, ?>)attributes.get("properties");
        String email = (String) map.get("email");
        return email;
    }

    public String getProfile(){
        Map<?, ?> map = (Map<?, ?>)attributes.get("properties");
        String profile = (String) map.get("profile");
        return profile;
    }



}
