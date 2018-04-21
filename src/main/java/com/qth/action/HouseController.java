package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.House;
import com.qth.service.IHouseService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/house")
@ResponseBody
public class HouseController extends BaseController{

@Autowired
IHouseService houseService;

/**
* 区域表格数据请求
* @param house
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<House> table(House house){

    DataTableRspWrapper<House> rspWrapper = houseService.selectDataTable2Rsp(house);
        //赋值绘制时序标识
        rspWrapper.setDraw(house.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param house
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(House house){
        int effect = houseService.insertHouse(house);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(House house){
        int effect = houseService.updateHouse(house);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        House house = houseService.findHouseById(id);
        return data2Rsp(house);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = houseService.deleteHouseById(id);
        return dbEffect2Rsp(effect);
    }

}
