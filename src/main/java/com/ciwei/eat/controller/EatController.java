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
    public List<CityTeamRs> eee(Model model){
        return ciweiEatTeamService.selectCityTeam();
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getTeam(){
        return "cityTeam";
    }


}
