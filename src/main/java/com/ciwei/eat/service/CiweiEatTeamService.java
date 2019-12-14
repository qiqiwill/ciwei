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

    private static List<CityTeamRs> cacheRs = new ArrayList<>();

    public static List<CityTeamRs> getCacheRs() {
        return cacheRs;
    }

    public static void setCacheRs(List<CityTeamRs> cacheRs) {
        CiweiEatTeamService.cacheRs = cacheRs;
    }

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
                String teamPersonNum = properties.getTeamPersonNum();
                String[] teamPersonNums = teamPersonNum.split(",");
                List<List<String>> teamUserRs = new ArrayList<>();
                for (int i=0; i< teamNum; i++){
                    int personNum =  getxxNum(teamPersonNums, i+1);
                    //先判断 这个组是否还有人数够分
                    if(personNum > v.size()){
                        break;
                    }else{
                        teamUserRs.add(v.subList(getxxNum(teamPersonNums, i) ,personNum));
                    }
                }
                cityTeamRs.setTeamUserList(teamUserRs);
                rs.add(cityTeamRs);
            }
        });
        cacheRs = rs;
        return rs;
    }

    private int getxxNum(String[] teamPersonNums, int index){
        int rs = 0;
        for (int i= 0; i< index; i++){
            rs = rs + Integer.parseInt(teamPersonNums[i]);
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
        if(teamProperties.getTeamNum() < 1){
            throw new Exception("组数量不能小于1");
        }
        if(teamProperties.getTeamPersonNum().split(",").length != teamProperties.getTeamNum()){
            throw new Exception("每组数量设置有误，与分组数量不一致，正确设置为 比如分组数量为2，每组数量设置为：2,3");
        }
        String[] personNum = teamProperties.getTeamPersonNum().split(",");
        for (String s: personNum){
            try {
                Integer.parseInt(s);
            }catch (Exception e){
                throw new Exception("每组数量设置需为整数 比如：2,3");
            }
        }
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
