package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.Region;
import com.qth.service.IRegionService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/region")
@ResponseBody
public class RegionController extends BaseController{

    @Autowired
    IRegionService regionService;

    /**
     * 区域表格数据请求
     * @param region
     * @return
     */
    @RequestMapping(path="table")
    public DataTableRspWrapper<Region> table(Region region){
        DataTableRspWrapper<Region> rspWrapper = regionService.selectDataTable2Rsp(region);
        //赋值绘制时序标识
        rspWrapper.setDraw(region.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     * @param region
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Region region){
        int effect = regionService.insertRegion(region);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Region region){
        int effect = regionService.updateRegion(region);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        Region region = regionService.findRegionById(id);
        return data2Rsp(region);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = regionService.deleteRegionById(id);
        return dbEffect2Rsp(effect);
    }

}
