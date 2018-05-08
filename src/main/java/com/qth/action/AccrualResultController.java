package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.AccrualResult;
import com.qth.service.IAccrualResultService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/accrualResult")
@ResponseBody
public class AccrualResultController extends BaseController{

@Autowired
IAccrualResultService accrualResultService;

/**
* 区域表格数据请求
* @param accrualResult
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<AccrualResult> table(AccrualResult accrualResult){

    DataTableRspWrapper<AccrualResult> rspWrapper = accrualResultService.selectDataTable2Rsp(accrualResult);
        //赋值绘制时序标识
        rspWrapper.setDraw(accrualResult.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param accrualResult
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(AccrualResult accrualResult){
        int effect = accrualResultService.insertAccrualResult(accrualResult);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(AccrualResult accrualResult){
        int effect = accrualResultService.updateAccrualResult(accrualResult);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        AccrualResult accrualResult = accrualResultService.findAccrualResultById(id);
        return data2Rsp(accrualResult);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = accrualResultService.deleteAccrualResultById(id);
        return dbEffect2Rsp(effect);
    }

}
