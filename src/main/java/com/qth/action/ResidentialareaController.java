package com.qth.action;

import com.qth.model.Residentialarea;
import com.qth.model.Street;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IResidentialareaService;
import com.qth.service.IStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("rest/residentialarea")
@ResponseBody
public class ResidentialareaController extends BaseController{

    @Autowired
    private IResidentialareaService residentialareaService;

    /**
     * 表格数据请求
     * @param residentialarea
     * @return
     */
    @RequestMapping(path="table")
    public DataTableRspWrapper<Residentialarea> table(Residentialarea residentialarea){
        DataTableRspWrapper<Residentialarea> rspWrapper = residentialareaService.selectDataTable2Rsp(residentialarea);
        //赋值绘制时序标识
        rspWrapper.setDraw(residentialarea.getDraw());
        return rspWrapper;
    }

//    /**
//     * 添加区域信息
//     * @param Street
//     * @return
//     */
//    @RequestMapping(value = "insert")
//    public CommonRsp insert(Street Street){
//        int effect = streetService.insertStreet(Street);
//        return dbEffect2Rsp(effect);
//    }
//
//    @RequestMapping(value = "update")
//    public CommonRsp update(Street Street){
//        int effect = streetService.updateStreet(Street);
//        return dbEffect2Rsp(effect);
//    }
//
//    @RequestMapping(value = "one")
//    public CommonRsp selectOne(Integer id){
//        Street Street = streetService.findStreetById(id);
//        return data2Rsp(Street);
//    }
//
//    @RequestMapping(value = "delete")
//    public CommonRsp deleteById(Integer id){
//        int effect = streetService.deleteStreetById(id);
//        return dbEffect2Rsp(effect);
//    }
}
