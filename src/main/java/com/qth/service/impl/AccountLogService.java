package com.qth.service.impl;

import com.qth.dao.AccountLogMapper;
import com.qth.model.AccountLog;
import com.qth.model.House;
import com.qth.model.HouseTree;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ReportForm;
import com.qth.service.IAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountLogService extends BaseService<AccountLog> implements IAccountLogService{

    @Autowired
    AccountLogMapper accountLogMapper;

    @Override
    public List<AccountLog> selectAll() {
        return accountLogMapper.selectAll();
    }

    @Override
    public int insertAccountLog(AccountLog accountLog) {
        return accountLogMapper.insert(accountLog);
    }

    @Override
    public int updateAccountLog(AccountLog accountLog) {
        return accountLogMapper.updateByPrimaryKey(accountLog);
    }

    @Override
    public AccountLog findAccountLogById(int id) {
        return accountLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteAccountLogById(int id) {
        return accountLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AccountLog> selectByHouseCode(String code) {
        return accountLogMapper.selectByHouseCode(code);
    }

    @Override
    public DataTableRspWrapper<AccountLog> reportSummary(ReportForm reportForm) {
        DataTableRspWrapper<AccountLog> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setDraw(reportForm.getDraw());
        sqlAppend(reportForm);
        rspWrapper.setData(accountLogMapper.selectReportSummary(reportForm));
        return rspWrapper;
    }

    @Override
    public DataTableRspWrapper<AccountLog> reportDetail(ReportForm reportForm) {
        DataTableRspWrapper<AccountLog> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setDraw(reportForm.getDraw());
        sqlAppend(reportForm);
        rspWrapper.setData(accountLogMapper.selectReportDetail(reportForm));
        rspWrapper.setRecordsTotal(accountLogMapper.selectReportDetailCount(reportForm));
        return rspWrapper;
    }

    private void sqlAppend(ReportForm reportForm){
        if (reportForm.getPaths()!=null && reportForm.getPaths().trim().length()>0) {
            //解析第一层路径，每个元素都是一个小区-房屋的层级关系
            String[] pathsArr = reportForm.getPaths().split(",");
            StringBuilder sqlOr = new StringBuilder("(");
            for (int i = 0; i < pathsArr.length; i++) {
                //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
                String[] ids = pathsArr[i].split("-");
                if(i>0){
                    sqlOr.append(" or ");
                }
                switch (ids.length - 1) {
                    case HouseTree.RESIDENTIALAREA_LEVEL:
                        //指定小区下所有房屋信息
                        sqlOr.append(" ra.id = ").append(ids[HouseTree.RESIDENTIALAREA_LEVEL]);
                        break;
                    case HouseTree.BUILDING_LEVEL:
                        sqlOr.append(" b.id = ").append(ids[HouseTree.BUILDING_LEVEL]);
                        break;
                    case HouseTree.UNIT_LEVEL:
                        sqlOr.append(" u.id = ").append(ids[HouseTree.UNIT_LEVEL]);
                        break;
                    case HouseTree.FLOOR_LEVEL:
                        House house = new House();
                        house.setUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                        house.setFloor(ids[HouseTree.FLOOR_LEVEL]);
                        sqlOr.append(" (u.id = ").append(ids[HouseTree.UNIT_LEVEL]).append(" and h.floor = ").append(ids[HouseTree.FLOOR_LEVEL]).append(")");
                        break;
                    case HouseTree.HOUSE_LEVEL:
                        sqlOr.append(" h.id = ").append(ids[HouseTree.HOUSE_LEVEL]);
                        break;
                    default:
                        break;
                }
            }
            sqlOr.append(")");
            if (sqlOr.length()>2) {
                reportForm.setSqlAppend(sqlOr.toString());
            }
        }
    }

}
