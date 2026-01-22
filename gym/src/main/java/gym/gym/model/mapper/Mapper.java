package gym.gym.model.mapper;


import gym.gym.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {


    @Select("select * from member")
    List<Member> findall();






}

