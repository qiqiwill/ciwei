package com.ciwei.eat.bean;

/**
 * @author LIUQI
 * @date 2019/12/3 21:39
 */
public class TeamProperties {

    private int id;

    private int teamNum;

    private String teamPersonNum;

    private String city;

    public TeamProperties() {
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public String getTeamPersonNum() {
        return teamPersonNum;
    }

    public void setTeamPersonNum(String teamPersonNum) {
        this.teamPersonNum = teamPersonNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
