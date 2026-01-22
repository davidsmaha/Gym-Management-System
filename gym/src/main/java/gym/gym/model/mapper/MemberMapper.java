package gym.gym.model.mapper;

import gym.gym.model.Member;
import gym.gym.model.Trainer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {





    @Select("SELECT * FROM schedule WHERE member_id = #{id}")
    Member findById(int id);


    @Insert("INSERT INTO member (first_name, last_name, email, status,phone_number,dob,gender,Password) VALUES (#{first_name}, #{last_name}, #{email}, #{status},#{phone_number},#{dob},#{gender},#{Password})")
    int insertUser(Member member) ;



    @Update({
            "<script>",
            "UPDATE member",
            "<set>",
            "<if test='first_name != null and first_name.trim() != \"\"'>first_name = #{first_name},</if>",
            "<if test='last_name != null and last_name.trim() != \"\"'>last_name = #{last_name},</if>",
            "<if test='gender != null and gender.trim() != \"\"'>gender = #{gender},</if>",
            "<if test='email != null and email.trim() != \"\"'>email = #{email},</if>",
            "<if test='phone_number != 0'>phone_number = #{phone_number},</if>",
            "<if test='status != null and status.trim() != \"\"'>status = #{status},</if>",
            "<if test='dob != null'>dob = #{dob},</if>",
            "<if test='updated_date != null'>updated_date = #{updated_date},</if>",
            "</set>",
            "WHERE member_id = #{member_id}",
            "</script>"
    })
    int updateMember(Member member);

@Select({
    "<script>",
            "SELECT * FROM member",
            "WHERE 1=1",
            "<if test='first_name != null'>",
            "AND first_name LIKE CONCAT(#{first_name}, '%')",
            "</if>",
            "<if test='id != null'>",
            "AND member_id = #{id}",
            "</if>",
            "<if test='status != null'>",
            "AND status = #{status}",
            "</if>",
            "LIMIT #{size} OFFSET #{offset}",
            "</script>"
})
        List<Member> findByFilters(@Param("first_name") String firstName,
                                   @Param("id") Integer id,
                                   @Param("status") String status,
                                   @Param("size") int size,
                                   @Param("offset") int offset);

    @Delete("DELETE FROM member WHERE member_id = #{member_id}")
   int deleteMemberById(int memberId);

    @Select("SELECT email, member_id, password FROM member WHERE email = #{email} AND member_id = #{memberId} AND password = #{password}")
    Member findByEmailAndIdAndPass(String email, Integer memberId, String password);

}

