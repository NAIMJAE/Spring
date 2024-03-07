package kr.co.ch05.Mapper;

import kr.co.ch05.DTO.MemberDTO;
import kr.co.ch05.DTO.ParentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MemberMapper {
    public void insertMember(MemberDTO memberDTO);
    public MemberDTO selectMember(String uid);
    public List<MemberDTO> selectMembers();
    public List<MemberDTO> selectMembersForSearch(@Param("type") String type, @Param("value") String value, @Param("pos") String[] pos);
    public List<ParentDTO> selectParentWithChild();
    public void updateMember(MemberDTO memberDTO);
    public void deleteMember(String uid);
}
