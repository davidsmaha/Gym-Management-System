package gym.gym.model.mapper;

import gym.gym.model.Exercise;
import gym.gym.model.Trainer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ExerciseMapper {


    @Select("select * from exercise")
    List<Exercise> findall();


    @Select("SELECT * FROM exercise WHERE exercise_id = #{id}")
    Exercise findExById(int id);

    @Insert("INSERT INTO exercise ( name, description,status) VALUES (#{name}, #{description}, #{status})")
    int insertexercise(Exercise exercise) ;

    @Update({
            "<script>",
            "UPDATE exercise",
            "<set>",
            "<if test='name != null'>name = #{name},</if>",
            "<if test='description != null'>description = #{description},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "<if test='updated_date != null'>updated_date = #{updated_date},</if>",
            "</set>",
            "WHERE exercise_id = #{exercise_id}",
            "</script>"
    })
    int updateExercise(Exercise exercise);
}
