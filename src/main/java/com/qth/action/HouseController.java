package com.qth.action;

import com.qth.model.AlgorithmSwitch;
import com.qth.model.ChargeBill;
import com.qth.model.ChargeCriterion;
import com.qth.model.House;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.HouseTreeModel;
import com.qth.service.IChargeBillService;
import com.qth.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "rest/house")
@ResponseBody
public class HouseController extends BaseController {

    @Autowired
    IHouseService houseService;

    @Autowired
    IChargeBillService chargeBillService;

    /**
     * 区域表格数据请求
     *
     * @param house
     * @return
     */
    @RequestMapping(path = "table")
    public DataTableRspWrapper<House> table(House house) {

        DataTableRspWrapper<House> rspWrapper = houseService.selectDataTable2Rsp(house);
        //赋值绘制时序标识
        rspWrapper.setDraw(house.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     *
     * @param house
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(House house) {
        int effect = houseService.insertHouse(house);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(House house) {
        int effect = houseService.updateHouse(house);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id) {
        House house = houseService.findHouseById(id);
        return data2Rsp(house);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id) {
        int effect = houseService.deleteHouseById(id);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "selectByTreeNode")
    public DataTableRspWrapper<House> selectByTreeNode(HouseTreeModel model){
        DataTableRspWrapper<House> rspWrapper = new DataTableRspWrapper<>();
        //赋值绘制时序标识
        rspWrapper.setDraw(model.getDraw());
        rspWrapper.setData(houseService.selectByTreeNode(model));
        rspWrapper.setRecordsTotal(houseService.selectCountByTreeNode(model));
        return rspWrapper;
    }

    @RequestMapping(value = "updateOwnerInfo")
    public CommonRsp updateOwnerInfo(House house){
        int effect = houseService.updateOwnerInfo(house);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "chargeInfo")
    public CommonRsp getHouseChargeInfo(Integer house,boolean patch, HttpSession session){
        CommonRsp rsp = new CommonRsp();
        //todo user company
        ChargeCriterion chargeCriterion = houseService.getChargeCriterionByHouse(house,1);
        AlgorithmSwitch algorithmSwitch = houseService.getChargeType(1);
        if (chargeCriterion!=null && algorithmSwitch !=null) {
            rsp.setSuccess(true);
            rsp.setResultCode("0000");
            Map map = new HashMap();
            map.put("algorithmSwitch",algorithmSwitch);
            map.put("chargeCriterion",chargeCriterion);
            if(patch){
                map.put("chargeBillCount",chargeBillService.countByHouse(house));
            }
            rsp.setAttr(map);
            return rsp;
        }else if(algorithmSwitch==null){
            rsp.setSuccess(false);
            rsp.setResultCode("8000");
            rsp.setDescription("没有匹配的缴存标准!");
            return rsp;
        }else if(chargeCriterion == null){
            rsp.setSuccess(false);
            rsp.setResultCode("8000");
            rsp.setDescription("没有匹配的缴存算法!");
        }
        return rsp;
    }

    @RequestMapping(value = "genChargeBill")
    public CommonRsp genChargeBill(ChargeBill chargeBill,HttpSession session){
        //todo handler
        Date stamp = new Date();

        chargeBill.setCreateTime(stamp);
        chargeBill.setState(0);
        chargeBill.setHandler("admin");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        chargeBill.setInvoiceNum(sdf.format(stamp));
        int effect = chargeBillService.insertChargeBill(chargeBill);
        CommonRsp rsp = dbEffect2Rsp(effect);
        rsp.setDescription("成功生成缴费单!");
        rsp.setData(chargeBill);
        return rsp;
    }

    @RequestMapping(value = "chargeBillPatch")
    public CommonRsp chargeBillPatch(ChargeBill chargeBill,HttpSession session){
        //todo handler
        Date stamp = new Date();
        chargeBill.setCreateTime(stamp);
        chargeBill.setState(2);
        chargeBill.setHandler("admin");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        chargeBill.setInvoiceNum(sdf.format(stamp));
        int effect = chargeBillService.insertChargeBill(chargeBill);
        CommonRsp rsp = dbEffect2Rsp(effect);
        rsp.setDescription("成功生成缴费单!");
        rsp.setData(chargeBill);
        return rsp;
    }

}
