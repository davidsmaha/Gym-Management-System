package gym.gym.model.mapper;

import gym.gym.model.Schedule_exercise;
import gym.gym.model.Trainer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScheduleExerciseMapper {


    @Select("select * from schedule_exercise")
    List<Schedule_exercise> findall();



    @Insert("INSERT INTO schedule_exercise (schedule_id, exercise_id, `order`, nb_of_repetition, status) " +
            "VALUES (#{schedule_id}, #{exercise_id}, #{order}, #{nb_of_repetition}, #{status})")
    int insertexerciseschedule(Schedule_exercise schedule_exercise);

    @Update({
            "<script>",
            "UPDATE schedule_exercise",
            "<set>",
            "<if test='schedule_id != null'>schedule_id = #{schedule_id},</if>",
            "<if test='exercise_id != null'>exercise_id = #{exercise_id},</if>",
            "<if test='order != null'>order = #{order},</if>",
            "<if test='nb_of_repetition != null'>nb_of_repetition = #{nb_of_repetition},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    int updateScheduleExercise(Schedule_exercise scheduleExercise);

}
