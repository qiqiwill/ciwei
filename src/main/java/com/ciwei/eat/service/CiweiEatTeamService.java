package com.ciwei.eat.service;

import com.ciwei.eat.bean.CityTeamRs;
import com.ciwei.eat.bean.TeamProperties;
import com.ciwei.eat.bean.User;
import com.ciwei.eat.dao.TeamMapper;
import com.ciwei.eat.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LIUQI
 * @date 2019/12/3 20:35
 */
@Service
public class CiweiEatTeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    public List<CityTeamRs> selectCityTeam(){
        List<CityTeamRs> rs = new ArrayList<>();

        List<User> list =  userMapper.selectUser();

        Map<String, List<String>> cityUserMap = list.stream().collect(Collectors.groupingBy(User::getCity, Collectors.mapping(User::getName, Collectors.toList())));

        List<TeamProperties> teamProperties = teamMapper.selectTeamProperties();
        cityUserMap.forEach((k,v)->{
            CityTeamRs cityTeamRs = new CityTeamRs();
            List<TeamProperties> teamPropertiesList = teamProperties.stream().filter(o->{return o.getCity().equals(k);}).collect(Collectors.toList());
            if(teamPropertiesList != null && !teamPropertiesList.isEmpty()){
                cityTeamRs.setCity(k);
                Collections.shuffle(v);
                TeamProperties properties = teamPropertiesList.get(0);
                //根据 每组多少人，多少个组 查出多少个人
                int teamNum = properties.getTeamNum();
                int teamPersonNum = properties.getTeamPersonNum();
                List<List<String>> teamUserRs = new ArrayList<>();
                for (int i=0; i< teamNum; i++){
                    if((i+1)*teamPersonNum > v.size()){
                        break;
                    }else{
                        teamUserRs.add(v.subList(i*teamPersonNum ,(i+1)*teamPersonNum));
                    }
                }
                cityTeamRs.setTeamUserList(teamUserRs);
                rs.add(cityTeamRs);
            }
        });
        return rs;
    }

    public List<List<String>> select(){
        //查出所有用户名称
        List<String> ciweiUserNameList = userMapper.selectBy();

        Collections.shuffle(ciweiUserNameList);

        TeamProperties properties = teamMapper.selectProperties();
        //根据 每组多少人，多少个组 查出多少个人
        int teamNum = properties.getTeamNum();
        int teamPersonNum = properties.getTeamPersonNum();

        List<List<String>> rs = new ArrayList<>();

        for (int i=0; i< teamNum; i++){
            rs.add(ciweiUserNameList.subList(i*teamPersonNum ,(i+1)*teamPersonNum));
        }
        return rs;
    }

    public List<User> selectUser(){
        return userMapper.selectUser();
    }

    public void saveUser(User user){
        if(user.getId() ==0){
            userMapper.insert(user);
        }else{
            userMapper.saveUser(user);
        }
    }

    //删除用户
    public void deleteUser(int id){
        userMapper.deleteUser(id);
    }

    //获取所有每组配置
    public List<TeamProperties> selectTeamProperties(){
        return teamMapper.selectTeamProperties();
    }

    //保存每组配置
    public void saveTeamProperties(TeamProperties teamProperties) throws Exception {
        if(teamMapper.selectByCity(teamProperties.getCity(), teamProperties.getId()) > 0){
            throw new Exception("城市不能重复");
        }
        if(teamProperties.getId() ==0){
            teamMapper.insertTeamProperties(teamProperties);
        }else{
            teamMapper.saveTeamProperties(teamProperties);
        }
    }

    //删除每组配置
    public void deletTeamProperties(int id){
        teamMapper.deleteTeamProperties(id);
    }

}
