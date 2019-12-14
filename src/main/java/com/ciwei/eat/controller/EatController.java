package com.ciwei.eat.controller;

import com.ciwei.eat.bean.CityTeamRs;
import com.ciwei.eat.service.CiweiEatTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * @author LIUQI
 * @date 2019/12/3 16:57
 */
@Controller
public class EatController {

    @Autowired
    private CiweiEatTeamService ciweiEatTeamService;

    @ResponseBody
    @RequestMapping(path = "/start", method = RequestMethod.GET)
    public List<CityTeamRs> start(){
        return ciweiEatTeamService.selectCityTeam();
    }

    @ResponseBody
    @RequestMapping(path = "/init", method = RequestMethod.GET)
    public List<CityTeamRs> init(){
        List<CityTeamRs> rs = CiweiEatTeamService.getCacheRs();
        if(rs.isEmpty()){
            return ciweiEatTeamService.selectCityTeam();
        }else {
            return rs;
        }
    }


    @RequestMapping(path = "/indexhr", method = RequestMethod.GET)
    public String index(Model mode){
        mode.addAttribute("isShow", true);
        return "cityTeam";
    }
    @RequestMapping(path = {"/index", "/"}, method = RequestMethod.GET)
    public String init(Model mode){
        mode.addAttribute("isShow", false);
        return "cityTeam";
    }

}
