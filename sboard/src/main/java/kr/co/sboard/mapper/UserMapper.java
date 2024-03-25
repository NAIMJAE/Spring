package kr.co.sboard.mapper;

import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public TermsDTO selectTerms();
    public void insertUser(UserDTO userDTO);
    public String selectUserForNick(String uid);
    public UserDTO selectUserForNameAndEmail(String name, String email);
    public UserDTO selectUserForUidAndEmail(String uid, String email);
    public int updateUserPassword(String password, String uid);
    public void updateUser();
    public void deleteUser();
    public int selectCheckUid(String uid);
    public int checkUser(String type, String value);
}
