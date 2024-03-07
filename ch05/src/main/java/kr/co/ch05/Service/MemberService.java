package kr.co.ch05.Service;

import kr.co.ch05.DTO.MemberDTO;
import kr.co.ch05.DTO.ParentDTO;
import kr.co.ch05.Mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MemberService {

    private final MemberMapper mapper;

    public MemberService(MemberMapper mapper) {
        this.mapper = mapper;
    }

    public void insertMember(MemberDTO memberDTO){
        mapper.insertMember(memberDTO);
    }

    public MemberDTO selectMember(String uid){
        return mapper.selectMember(uid);
    }

    public List<MemberDTO> selectMembers(){
        return mapper.selectMembers();
    }

    public List<MemberDTO> selectMembersForSearch(String type, String value, String[] pos){
        return mapper.selectMembersForSearch(type, value, pos);
    }

    public List<ParentDTO> selectParentWithChild(){
        return mapper.selectParentWithChild();
    }

    public void updateMember(MemberDTO memberDTO){
        mapper.updateMember(memberDTO);
    }

    public void deleteMember(String uid){
        mapper.deleteMember(uid);
    }
}
