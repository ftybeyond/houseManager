package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.AlgorithmSwitch;
import com.qth.service.IAlgorithmSwitchService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/algorithmSwitch")
@ResponseBody
public class AlgorithmSwitchController extends BaseController {

    @Autowired
    IAlgorithmSwitchService algorithmSwitchService;

    /**
     * 区域表格数据请求
     *
     * @param algorithmSwitch
     * @return
     */
    @RequestMapping(path = "table")
    public DataTableRspWrapper<AlgorithmSwitch> table(AlgorithmSwitch algorithmSwitch) {
        DataTableRspWrapper<AlgorithmSwitch> rspWrapper = algorithmSwitchService.selectDataTable2Rsp(algorithmSwitch);
        //赋值绘制时序标识
        rspWrapper.setDraw(algorithmSwitch.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     *
     * @param algorithmSwitch
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(AlgorithmSwitch algorithmSwitch) {
        int effect = algorithmSwitchService.insertAlgorithmSwitch(algorithmSwitch);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(AlgorithmSwitch algorithmSwitch) {
        int effect = algorithmSwitchService.updateAlgorithmSwitch(algorithmSwitch);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id) {
        AlgorithmSwitch algorithmSwitch = algorithmSwitchService.findAlgorithmSwitchById(id);
        return data2Rsp(algorithmSwitch);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id) {
        int effect = algorithmSwitchService.deleteAlgorithmSwitchById(id);
        return dbEffect2Rsp(effect);
    }

}
