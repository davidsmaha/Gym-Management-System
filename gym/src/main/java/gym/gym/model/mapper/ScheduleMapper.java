package gym.gym.model.mapper;

import gym.gym.model.Schedule;
import gym.gym.model.Trainer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {



    @Select("SELECT * FROM member WHERE member_id = #{id}")
    List<Schedule> findById(int id);


    @Select("SELECT * FROM schedule WHERE member_id = #{id}")
    List<Schedule> findSchedByMembId(int id);


    @Select("select * from schedule")
    List<Schedule> findall();


    @Select("SELECT * FROM schedule WHERE schedule_id = #{scheduleId}")
    Schedule findScheduleById(int scheduleId);


    @Insert("INSERT INTO schedule (date, status, member_id) VALUES (#{date}, #{status}, #{member_id})")
    int insertschedule(Schedule schedule);


    @Update({
            "<script>",
            "UPDATE schedule",
            "<set>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE schedule_id = #{schedule_id}",
            "</script>"
    })
    int updateSchedule(Schedule schedule);
}

