package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.Building;
import com.qth.service.IBuildingService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/building")
@ResponseBody
public class BuildingController extends BaseController{

@Autowired
IBuildingService buildingService;

/**
* 区域表格数据请求
* @param building
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<Building> table(Building building){

    DataTableRspWrapper<Building> rspWrapper = buildingService.selectDataTable2Rsp(building);
        //赋值绘制时序标识
        rspWrapper.setDraw(building.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param building
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Building building){
        int effect = buildingService.insertBuilding(building);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Building building){
        int effect = buildingService.updateBuilding(building);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        Building building = buildingService.findBuildingById(id);
        return data2Rsp(building);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = buildingService.deleteBuildingById(id);
        return dbEffect2Rsp(effect);
    }

}
