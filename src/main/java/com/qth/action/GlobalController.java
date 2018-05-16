package com.qth.action;

import com.qth.model.Company;
import com.qth.model.House;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.Select2;
import com.qth.model.common.SelectIdstring;
import com.qth.model.dto.ChargeBillPrintInfo;
import com.qth.service.IChargeBillService;
import com.qth.service.ICompanyService;
import com.qth.service.ISelectService;
import com.qth.util.DateUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 全局管控controller
 */
@Controller
public class GlobalController {               

    @Autowired
    ISelectService selectService;

    @Autowired
    IChargeBillService chargeBillService;

    @Autowired
    ICompanyService companyService;

    @Value("${busi.coreCompany.id}")
    Integer company;

    @RequestMapping(value = "/forward/chargeBillPrint")
    public ModelAndView chargeBillPrint(Integer id){
        //todo handler
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        if(id!=null){
            ChargeBillPrintInfo printInfo = chargeBillService.getPrintInfo(id);
            if(printInfo==null){
                return modelAndView;
            }
            Company coreCompany = companyService.findCompanyById(company);
            printInfo.setHandler("admin");
            printInfo.setStamp(DateUtils.formatDate(new Date(),"yyyy-MM-dd"));
            printInfo.setCompanyAccount(coreCompany.getAccountNum());
            printInfo.setCompanyName(coreCompany.getName());
            modelAndView.addObject("printInfo",printInfo);
            modelAndView.setViewName("chargeBillPrint");
        }
        return modelAndView;
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param table 数据字典表明
     * @return
     */
    @RequestMapping("/rest/select/{table}")
    @ResponseBody
    public List<Select2> getSelectData(@PathVariable String table) {
        return selectService.getAll(table);
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param region region
     * @return
     */
    @RequestMapping("/rest/selectStreetByRegion/{region}")
    @ResponseBody
    public List<Select2> getSelectStreetDataByRegion(@PathVariable Integer region) {
        return selectService.getStreetByRegion(region);
    }

    /**
     * 通用select2组件数据请求服务
     * @param street street
     * @return
     */
    @RequestMapping("/rest/selectResidentialAreaByStreet/{street}")
    @ResponseBody
    public List<Select2> getResidentialAreaDataByRegion(@PathVariable Integer street){
        return selectService.getResidentialAreaByRegion(street);
    }

    /**
     * 通用select2组件数据请求服务
     * @param residentialArea residentialArea
     * @return
     */
    @RequestMapping("/rest/selectBuildingByResidentialArea/{residentialArea}")
    @ResponseBody
    public List<Select2> getBuildingDataByResidentialArea(@PathVariable Integer residentialArea){
        return selectService.getBuildingDataByResidentialArea(residentialArea);
    }

    /**
     * 通用select2组件数据请求服务
     * @param building building
     * @return
     */
    @RequestMapping("/rest/selectUnitByBuilding/{building}")
    @ResponseBody
    public List<Select2> getUnitDataByBuilding(@PathVariable Integer building){
        return selectService.getUnitDataByBuilding(building);
    }


    /**
     * 通用select2组件数据请求服务
     * @param unit unit
     * @return
     */
    @RequestMapping("/rest/selectFloorByUnit/{unit}")
    @ResponseBody
    public List<Select2> getFloorDataByUnit(@PathVariable Integer unit){
        return selectService.getFloorDataByUnit(unit);
    }
    /**
     * 通用select2组件数据请求服务
     * @param house house
     * @return
     */
    @RequestMapping("/rest/selectHouseNameByUnitFloor/get")
    @ResponseBody
    public List<SelectIdstring> getHouseNameByUnitFloor(House house){
        return selectService.getHouseNameDataByUnitFloor(house);
    }
    /**
     * 通用select2组件数据请求服务
     *
     * @param type type
     * @return
     */
    @RequestMapping("/rest/selectConfigSelect/{type}")
    @ResponseBody
    public List<Select2> getConfigSelect(@PathVariable String type) {
        return selectService.getConfigSelect(type);
    }

}