package com.ciwei.eat.bean;

import java.util.List;

/**
 * @author LIUQI
 * @date 2019/12/12 22:23
 */
public class CityTeamRs {

    public String city;

    public List<List<String>> teamUserList;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<List<String>> getTeamUserList() {
        return teamUserList;
    }

    public void setTeamUserList(List<List<String>> teamUserList) {
        this.teamUserList = teamUserList;
    }
}
