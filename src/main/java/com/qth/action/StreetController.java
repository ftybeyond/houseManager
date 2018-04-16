package com.qth.action;

import com.qth.model.Street;
import com.qth.service.IStreetService;
import com.qth.util.CommonRsp;
import com.qth.util.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("rest/street")
@ResponseBody
public class StreetController extends BaseController{

    @Autowired
    private IStreetService streetService;

    /**
     * 区域表格数据请求
     * @param Street
     * @return
     */
    @RequestMapping(path="table")
    public DataTableRspWrapper<Street> table(Street Street){
        DataTableRspWrapper<Street> rspWrapper = streetService.selectDataTable2Rsp(Street);
        //赋值绘制时序标识
        rspWrapper.setDraw(Street.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     * @param Street
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Street Street){
        int effect = streetService.insertStreet(Street);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Street Street){
        int effect = streetService.updateStreet(Street);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        Street Street = streetService.findStreetById(id);
        return data2Rsp(Street);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = streetService.deleteStreetById(id);
        return dbEffect2Rsp(effect);
    }
}
