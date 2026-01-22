package gym.gym.model.mapper;

import gym.gym.model.Trainer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainerMapper {

    @Select("select * from trainer")
    List<Trainer> findall();

    @Insert("INSERT INTO trainer (first_name, last_name, email,phone_number,status,speciality,Password) VALUES (#{first_name}, #{last_name}, #{email}, #{phone_number},#{status},#{speciality},#{Password})")
    int inserttrainer(Trainer trainer) ;

    @Select("SELECT * FROM trainer WHERE trainer_id = #{id}")
    Trainer findTrainerById(int id);

    @Update({
            "<script>",
            "UPDATE trainer",
            "<set>",
            "<if test='first_name != null and first_name.trim() != \"\"'>first_name = #{first_name},</if>",
            "<if test='last_name != null and last_name.trim() != \"\"'>last_name = #{last_name},</if>",
            "<if test='email != null and email.trim() != \"\"'>email = #{email},</if>",
            "<if test='phone_number != 0'>phone_number = #{phone_number},</if>",
            "<if test='status != null and status.trim() != \"\"'>status = #{status},</if>",
            "<if test='speciality != null and speciality.trim() != \"\"'>speciality = #{speciality},</if>",
            "<if test='updated_date != null'>updated_date = #{updated_date},</if>",
            "</set>",
            "WHERE trainer_id = #{trainer_id}",
            "</script>"
            })
    int updateTrainer(Trainer trainer);


    @Select({
            "<script>",
            "SELECT * FROM trainer",
            "WHERE 1=1",
            "<if test='first_name != null'>",
            "AND first_name LIKE CONCAT(#{first_name}, '%')",
            "</if>",
            "<if test='id != null'>",
            "AND trainer_id = #{id}",
            "</if>",
            "<if test='status != null'>",
            "AND status = #{status}",
            "</if>",
            "LIMIT #{size} OFFSET #{offset}",
            "</script>"
    })
    List<Trainer> findByFilters(@Param("first_name") String firstName,
                                @Param("id") Integer id,
                                @Param("status") String status,
                                @Param("size") int size,
                                @Param("offset") int offset);

    @Delete("DELETE FROM trainer WHERE trainer_id = #{trainerId}")
    int deleteTrainerById(int trainerId);

    @Select("SELECT * FROM trainer WHERE email = #{email} AND trainer_id = #{trainerId} AND password = #{password}")
    Trainer findByEmailAndIdAndPass(String email, int trainerId, String password);

}
