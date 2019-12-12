package com.ciwei.eat.dao;

import com.ciwei.eat.bean.TeamProperties;
import com.ciwei.eat.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LIUQI
 * @date 2019/12/3 20:18
 */
@Mapper
public interface TeamMapper {

    @Select("<script>select count(*) from team_properties where city = #{city} <if test='id >0'>and id != #{id}</if> </script>")
    int selectByCity(@Param("city") String city, @Param("id") int id);

    @Select("select id, team_num as teamNum, team_person_num as teamPersonNum, city from team_properties")
    List<TeamProperties> selectTeamProperties();

    @Select("select team_num as teamNum, team_person_num as teamPersonNum from team_properties")
    TeamProperties selectProperties();

    @Update("update team_properties set team_num = #{teamNum}, team_person_num = #{teamPersonNum},city = #{city} where id = #{id}")
    void saveTeamProperties(TeamProperties teamProperties);

    @Insert("insert into team_properties(team_num, team_person_num, city) values(#{teamNum}, #{teamPersonNum}, #{city})")
    void insertTeamProperties(TeamProperties teamProperties);

    @Delete("delete from team_properties where id = #{id}")
    void deleteTeamProperties(int id);
}
