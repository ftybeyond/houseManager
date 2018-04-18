package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.ResidentialArea;
import com.qth.service.IResidentialAreaService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/residentialArea")
@ResponseBody
public class ResidentialAreaController extends BaseController{

@Autowired
IResidentialAreaService residentialAreaService;

/**
* 区域表格数据请求
* @param residentialArea
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<ResidentialArea> table(ResidentialArea residentialArea){
    DataTableRspWrapper<ResidentialArea> rspWrapper = residentialAreaService.selectDataTable2Rsp(residentialArea);
        //赋值绘制时序标识
        rspWrapper.setDraw(residentialArea.getDraw());
        return rspWrapper;
        }

        /**
        * 添加区域信息
        * @param residentialArea
        * @return
        */
        @RequestMapping(value = "insert")
        public CommonRsp insert(ResidentialArea residentialArea){
        int effect = residentialAreaService.insertResidentialArea(residentialArea);
        return dbEffect2Rsp(effect);
        }

        @RequestMapping(value = "update")
        public CommonRsp update(ResidentialArea residentialArea){
        int effect = residentialAreaService.updateResidentialArea(residentialArea);
        return dbEffect2Rsp(effect);
        }

        @RequestMapping(value = "one")
        public CommonRsp selectOne(Integer id){
        ResidentialArea residentialArea = residentialAreaService.findResidentialAreaById(id);
        return data2Rsp(residentialArea);
        }

        @RequestMapping(value = "delete")
        public CommonRsp deleteById(Integer id){
        int effect = residentialAreaService.deleteResidentialAreaById(id);
        return dbEffect2Rsp(effect);
        }

        }
