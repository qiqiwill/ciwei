package com.ciwei.eat.dao;

import com.ciwei.eat.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LIUQI
 * @date 2019/12/12 15:24
 */
@Mapper
public interface UserMapper {

    @Select("select id, name, city from eat order by CONVERT(name USING gbk)")
    List<User> selectUser();

    @Insert("insert into eat(name,city) values(#{name},#{city})")
    void insert(User user);

    @Update("update eat set name = #{name}, city = #{city} where id = #{id}")
    void saveUser(User user);

    @Delete("delete from eat where id = #{id}")
    void deleteUser(int id);

    @Select("select name from eat")
    List<String> selectBy();

}
