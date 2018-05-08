package com.qth.service.impl;

import com.qth.dao.AccountLogMapper;
import com.qth.dao.AccrualResultMapper;
import com.qth.dao.MoneyRateMapper;
import com.qth.model.AccountLog;
import com.qth.model.AccrualResult;
import com.qth.model.House;
import com.qth.model.MoneyRate;
import com.qth.service.IAccrualResultService;
import com.qth.service.IAccrualService;
import com.qth.service.IHouseService;
import com.qth.util.AccountLogRateChangeComparator;
import com.qth.util.AccountLogRateChangeComparatorHelper;
import com.qth.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

@Service
@Transactional
public class AccrualService implements IAccrualService {

    Logger logger = Logger.getLogger(AccrualService.class.getName());

    @Autowired
    MoneyRateMapper rateMapper;

    @Autowired
    IHouseService houseService;

    @Autowired
    IAccrualResultService accrualResultService;

    @Autowired
    AccountLogMapper accountLogMapper;

    @Autowired
    AccrualResultMapper accrualResultMapper;

    /**
     * 计算所选房屋利息
     *
     * @param paths  树形结构目录
     * @param toDate 计息开始日期
     * @return 计息条数
     */
    @Override
    @Transactional
    public int accrualCalculate(String paths, Date toDate) {
        //待计息房屋列表
        List<House> houseList = houseService.selectByTreePath(paths);
        //利息变化列表
        List<MoneyRate> moneyRates = rateMapper.selectAll();
        int count = 0;
        Date stamp = new Date();
        for (House house : houseList) {

            if (house.getAccrualTime()==null||house.getAccrualBalance()==null) {
                //无余额变更记录，说明此账户没有登帐，不计息
                logger.info("房屋："+house.getCode() + "无初始计息记录，不计息...");
                continue;
            }
            //房屋结算利息值
            BigDecimal accrual = new BigDecimal(0f);
            //计息开始时间--初始为上次结算日期
            Date startDate = house.getAccrualTime();
            //得出针对此房屋的有效计息区间
            List<MoneyRate> validRate = handleMoneyRate(moneyRates,startDate);
            if(validRate==null){
                logger.info("利息期间缺失!");
                throw new RuntimeException("利息期间缺失!");
            }
            ///计息结束时间--
            Date endDate;
            Map param = new HashMap();
            param.put("houseCode", house.getCode());
            param.put("fromDate", startDate);
            param.put("endDate", toDate);
            List<AccountLog> accountLogs = accountLogMapper.selectHouseChangeWithDateRange(param);


            //融合日志变更记录和利息变更记录表，用于排序，根据变化时间点排序
            List<AccountLogRateChangeComparatorHelper> sortList = new ArrayList(accountLogs);
            sortList.addAll(validRate);
            sortList.sort(new AccountLogRateChangeComparator());
            //计息余额--初始余额为上次结息时余额
            BigDecimal balance = house.getAccrualBalance();
            //计息利率--初始利率
            BigDecimal rate = validRate.get(0).getRate();
            for (AccountLogRateChangeComparatorHelper helper : sortList) {
                if(helper.compareDateElement().compareTo(toDate)>=0){
                    //事件发生已超出结息日期范围
                    endDate = toDate;
                    break;
                }
                if (helper instanceof AccountLog) {
                    //时间点上余额变化
                    AccountLog log = (AccountLog) helper;
                    balance = log.getBalance();
                    if (balance.compareTo(new BigDecimal(0f)) < 1) {
                        //余额小于等于0，此阶段不计息
                        continue;
                    }
                } else if (helper instanceof MoneyRate) {
                    MoneyRate moneyRate = (MoneyRate) helper;
                    rate = moneyRate.getRate();
                }else{
                    logger.info("计息时间节点解析错误!");
                    throw new RuntimeException("计息时间节点解析错误!");
                }
                //设置此阶段的截至日期
                endDate = helper.compareDateElement();
                Long days = DateUtils.getDateDiff(endDate, startDate);
                if(days>0){
                    //计算阶段利息
                    accrual = balance.multiply(rate).multiply(new BigDecimal(days));
                    //利息变化记录
                    AccrualResult accrualResult = new AccrualResult();
                    accrualResult.setBalance(balance);
                    accrualResult.setHouseCode(house.getCode());
                    accrualResult.setStartTime(startDate);
                    accrualResult.setEndTime(endDate);
                    accrualResult.setRate(rate);
                    accrualResult.setState(0);
                    accrualResult.setSeq(stamp.getTime());
                    accrualResult.setAccrual(accrual);
                    count += accrualResultMapper.insert(accrualResult);
                    startDate = endDate;
                }
            }
            endDate = toDate;
            Long days = DateUtils.getDateDiff(endDate, startDate);
            if(days>0){
                //计算阶段利息
                accrual = balance.multiply(rate).multiply(new BigDecimal(days));
                //利息变化记录
                AccrualResult accrualResult = new AccrualResult();
                accrualResult.setBalance(balance);
                accrualResult.setHouseCode(house.getCode());
                accrualResult.setStartTime(startDate);
                accrualResult.setEndTime(endDate);
                accrualResult.setRate(rate);
                accrualResult.setState(0);
                accrualResult.setSeq(stamp.getTime());
                accrualResult.setAccrual(accrual);
                count += accrualResultMapper.insert(accrualResult);
            }
        }
        return count;
    }

    @Override
    public int accrualBack(AccrualResult model) {
        List<AccrualResult> list = accrualResultService.selectByModel(model);
        return accrualResultService.delectBatch(list);
    }

    /**
     * 根据指定时间返回利率期间集合
     * @param moneyRateList 按时间排序的利率变更记录
     * @param start 开始计息时间
     * @return
     */
    private List<MoneyRate> handleMoneyRate(List<MoneyRate> moneyRateList,Date start){
        if(moneyRateList!=null&&moneyRateList.size()>0){
            for (int i = 0; i <moneyRateList.size() ; i++) {
                //如果利息期间大于开始计息时间
                if(moneyRateList.get(i).getTerm().compareTo(start)>=0){
                    if(i>=1){
                        return moneyRateList.subList(i-1,moneyRateList.size());
                    }else {
                        return null;
                    }
                }
            }
            //如果集合中所有利息时间都小于计息开始时间，则取最后一个利息
            List<MoneyRate> result = new ArrayList<>(1);
            result.add(moneyRateList.get(moneyRateList.size()-1));
            return result;
        }
        return null;
    }
}
