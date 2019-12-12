package com.ciwei.eat.controller.admin;

import com.ciwei.eat.bean.Result;
import com.ciwei.eat.bean.TeamProperties;
import com.ciwei.eat.bean.User;
import com.ciwei.eat.service.CiweiEatTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LIUQI
 * @date 2019/12/10 16:20
 */
@Controller
public class AdminController{

    @Autowired
    private CiweiEatTeamService ciweiEatTeamService;
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String ss(){
        return "admin/index";
    }
    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String admin(){
        return "admin/index";
    }

    /**
     * 获取员工数据
     */
    @RequestMapping(path = "/admin/user", method = RequestMethod.GET)
    @ResponseBody
    public List<User> user(){
        return ciweiEatTeamService.selectUser();
    }

    /**
     * 保存员工数据
     */
    @RequestMapping(path = "/admin/user", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(@RequestBody User user){
        ciweiEatTeamService.saveUser(user);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @ResponseBody
    @RequestMapping(path = "/admin/user/{id}", method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable("id") int id){
        ciweiEatTeamService.deleteUser(id);
        return Result.success();
    }

    /**
     * 查询每组配置
     */
    @RequestMapping(path = "/admin/teamSetting", method = RequestMethod.GET)
    @ResponseBody
    public List<TeamProperties> teamSetting(){
        return ciweiEatTeamService.selectTeamProperties();
    }

    /**
     * 保存配置
     * @param teamProperties
     * @return
     */
    @RequestMapping(path = "/admin/teamSetting", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTeamSetting(@RequestBody TeamProperties teamProperties){
        try {
            ciweiEatTeamService.saveTeamProperties(teamProperties);
        } catch (Exception e) {
            return new Result("001", e.getMessage());
        }
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(path = "/admin/teamSetting/{id}", method = RequestMethod.DELETE)
    public Result deleteTeamSetting(@PathVariable("id") int id){
        ciweiEatTeamService.deletTeamProperties(id);
        return Result.success();
    }
}
