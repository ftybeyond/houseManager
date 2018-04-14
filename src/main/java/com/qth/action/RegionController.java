package com.qth.action;

import com.qth.model.Region;
import com.qth.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "rest")
@ResponseBody
public class RegionController {

    @Autowired
    IRegionService regionService;

    @RequestMapping(path="regions")
    public List<Region> regionList(){
        return regionService.getRegionList();
    }

}
