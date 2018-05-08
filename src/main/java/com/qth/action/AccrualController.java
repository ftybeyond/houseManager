package com.qth.action;

import com.qth.model.AccrualResult;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IAccrualResultService;
import com.qth.service.IAccrualService;
import com.qth.service.IHouseService;
import com.qth.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AccrualController extends BaseController {

    @Autowired
    IHouseService houseService;

    @Autowired
    IAccrualResultService accrualResultService;

    @Autowired
    IAccrualService accrualService;

    @RequestMapping("/rest/accrual/lastDate")
    @ResponseBody
    public CommonRsp lastDate(String paths){
        CommonRsp rsp = new CommonRsp();
        AccrualResult accrualResult = new AccrualResult();
        accrualResult.setState(0);
        accrualResult.setPaths(paths);
        int count = accrualResultService.selectCountByModel(accrualResult);
        if(count >0){
            rsp.setSuccess(false);
            rsp.setResultCode("8888");
            rsp.setDescription("选定范围内有"+count +"条计息记录未登账，请先处理计息登帐！");
            return rsp;
        }

        Date date = houseService.getLastAccrual(paths);

        rsp.setSuccess(true);
        rsp.setResultCode("0000");
        if (date!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            rsp.setDescription("选定范围内最后计息时间为" + sdf.format(date));
        } else {
            rsp.setDescription("选定范围内无过往计息记录");
        }
        return rsp;
    }

    @RequestMapping("/rest/accrual/accrualCalculate")
    @ResponseBody
    public CommonRsp accrualCalculate(String paths,String toDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int count = accrualService.accrualCalculate(paths,dateFormat.parse(toDate));
        return description2Rsp("累计成功计息"+count + "条");
    }

    @RequestMapping("/rest/accrual/resultTable")
    @ResponseBody
    public DataTableRspWrapper<AccrualResult> table(AccrualResult accrualResult){
        List<AccrualResult> list = accrualResultService.selectByModel(accrualResult);
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        rspWrapper.setData(list);
        rspWrapper.setRecordsTotal(accrualResultService.selectCountByModel(accrualResult));
        rspWrapper.setDraw(accrualResult.getDraw());
        return rspWrapper;
    }

    @RequestMapping("/rest/accrual/accrualBack")
    @ResponseBody
    public CommonRsp accrualBack(AccrualResult accrualResult){
        int effect = accrualService.accrualBack(accrualResult);
        return dbEffect2Rsp(effect);
    }
}
