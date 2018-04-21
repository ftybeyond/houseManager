package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.Unit;
import com.qth.service.IUnitService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/unit")
@ResponseBody
public class UnitController extends BaseController {

    @Autowired
    IUnitService unitService;

    /**
     * 区域表格数据请求
     *
     * @param unit
     * @return
     */
    @RequestMapping(path = "table")
    public DataTableRspWrapper<Unit> table(Unit unit) {

        DataTableRspWrapper<Unit> rspWrapper = unitService.selectDataTable2Rsp(unit);
        //赋值绘制时序标识
        rspWrapper.setDraw(unit.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     *
     * @param unit
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Unit unit) {
        int effect = unitService.insertUnit(unit);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Unit unit) {
        int effect = unitService.updateUnit(unit);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id) {
        Unit unit = unitService.findUnitById(id);
        return data2Rsp(unit);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id) {
        int effect = unitService.deleteUnitById(id);
        return dbEffect2Rsp(effect);
    }

}
