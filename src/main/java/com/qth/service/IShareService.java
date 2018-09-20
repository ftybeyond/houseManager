package com.qth.service;

import com.qth.model.AccountLog;
import com.qth.model.House;
import com.qth.model.RepairRecord;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.common.ZTreeModel;
import com.qth.model.common.ZTreeNodeReq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IShareService {

    public List<ZTreeModel> loadTreeNodes(ZTreeNodeReq req);

    public List<House> checkShare(String paths,Integer shareType,BigDecimal sumArea,Integer totalHouse,BigDecimal cost ,Integer record);


        /**
         *
         * @param paths 树型结构路径
         * @param shareType 分摊类型
         * @param sumArea 总面积
         * @param houses  总户数
         * @param cost 总花销
         * @param handler 操作员
         * @return
         */
    public int share(String paths, Integer shareType, BigDecimal sumArea, Integer houses, BigDecimal cost,Integer record, String handler);

    public int shareBack(Integer record,String handler);

    public int shareAccount(Integer record,String handler);

    public int shareBackAccount(Integer record, String handler);

    public BigDecimal getRecordCost(Integer id);

    public int sumShareHouses(String paths);

    public BigDecimal sumShareArea(String paths);

    public Map<String,BigDecimal> shareBackInfo(Long seq);

    public DataTableRspWrapper<AccountLog> shareAccountDetail(AccountLog record);

    public double shareSumAccountDetail(AccountLog record);
}
