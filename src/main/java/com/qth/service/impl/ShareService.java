package com.qth.service.impl;

import com.qth.dao.AccountLogMapper;
import com.qth.dao.HouseMapper;
import com.qth.dao.RepairRecordMapper;
import com.qth.dao.ShareMapper;
import com.qth.model.AccountLog;
import com.qth.model.HouseTree;
import com.qth.model.RepairRecord;
import com.qth.model.House;
import com.qth.model.common.ZTreeModel;
import com.qth.model.common.ZTreeNodeReq;
import com.qth.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShareService implements IShareService{

    @Autowired
    ShareMapper shareMapper;

    @Autowired
    RepairRecordMapper recordMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    AccountLogMapper accountLogMapper;

    @Override
    public List<ZTreeModel> loadTreeNodes(ZTreeNodeReq req) {
        if(req.getLevel() == null){
            if (req.getParam()!=null) {
                return shareMapper.loadResidentialAreaNodes(Integer.parseInt(req.getParam()));
            } else {
                return shareMapper.loadResidentialAreaNodes(null);
            }
        }
        switch (req.getLevel()){
            case HouseTree.RESIDENTIALAREA_LEVEL:
                //当前点击小区节点，载入楼栋
                return shareMapper.loadBuildingNodes(req.getId());
            case HouseTree.BUILDING_LEVEL:
                //当前点击楼栋节点，载入单元
                return shareMapper.loadUnitNodes(req.getId());
            case HouseTree.UNIT_LEVEL:
                //当前点击单元节点，载入楼层
                return shareMapper.loadFloorNodes(req.getId());
            case HouseTree.FLOOR_LEVEL:
                //当前点击楼层节点，载入房屋
                return shareMapper.loadHouseNodes(req);
            default:
                //默认载入全部小区
                return shareMapper.loadResidentialAreaNodes(null);
        }
    }

    @Override
    @Transactional
    public List<House> share(String paths,Integer shareType,BigDecimal sumArea,Integer totalHouse,BigDecimal cost ,Integer record,String handler) {

        //解析第一层路径，每个元素都是一个小区-房屋的层级关系
        String[] pathsArr = paths.split(",");
        int sum = 0;
        List<House> unBalance = new ArrayList<>();
        Date stamp = new Date();
        for(String path:pathsArr){
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = path.split("-");
            List<House> list;
            switch (ids.length-1){
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    //指定小区下所有房屋信息
                    list = houseMapper.allHousesInResidentialArea(Integer.parseInt(ids[HouseTree.RESIDENTIALAREA_LEVEL]));
                    break;
                case HouseTree.BUILDING_LEVEL:
                    list = houseMapper.allHousesInBuilding(Integer.parseInt(ids[HouseTree.BUILDING_LEVEL]));
                    break;
                case HouseTree.UNIT_LEVEL:
                    list = houseMapper.allHousesInUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    break;
                case HouseTree.FLOOR_LEVEL:
                    House house = new House();
                    house.setUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    house.setFloor(ids[HouseTree.FLOOR_LEVEL]);
                    list = houseMapper.allHousesInFloor(house);
                    break;
                case HouseTree.HOUSE_LEVEL:
                    list = new ArrayList<>(1);
                    list.add(houseMapper.selectSimpleOne(Integer.parseInt(ids[HouseTree.HOUSE_LEVEL])));
                    break;
                default:
                    list = new ArrayList<>();
                    break;
            }
            sum += houseShare(list,shareType,sumArea,totalHouse,cost,handler,stamp,unBalance,record);
        }
        RepairRecord repairRecord = recordMapper.selectByPrimaryKey(record);
        repairRecord.setStamp(stamp);
        repairRecord.setState(1);
        repairRecord.setShareSeq(stamp.getTime());
        recordMapper.stateChange(repairRecord);
        return unBalance;
    }

    private int houseShare(List<House> houses,Integer shareType,BigDecimal sumArea,Integer totalHouse,BigDecimal cost,String handler,Date stamp,List<House> unBalance,Integer record){
        //每户花销
        BigDecimal houseCost;
        //平米均摊价，按面积分摊时使用
        BigDecimal everySquareCost = cost.divide(sumArea,2, BigDecimal.ROUND_HALF_EVEN);
        //每户均摊价，按户分摊时使用
        BigDecimal everyHouseCost = cost.divide(new BigDecimal(totalHouse),2, BigDecimal.ROUND_HALF_EVEN);

        BigDecimal totalCost = new BigDecimal(0f);
        for (int i=0;i<houses.size();i++){
            House house = houses.get(i);
            if(shareType == 1){
                //按面积分摊
                houseCost = everySquareCost.multiply(house.getArea());
            }else if (shareType == 2){
                //按户分摊
                houseCost = everyHouseCost;
            }else{
                return 0;
            }
            if(i == houses.size()-1){
                //最后一个 花费= 总额-前面总花销
                houseCost = cost.subtract(totalCost);
            }
            totalCost = totalCost.add(houseCost);
            //新的余额
            BigDecimal newBalance = house.getAccountBalance().subtract(houseCost);
            if(newBalance.compareTo(new BigDecimal(0f))==-1) {
                unBalance.add(house);
            }
            //余额变更记录
            AccountLog accountLog = new AccountLog();
            accountLog.setBalance(house.getAccountBalance());
            accountLog.setHouseCode(house.getCode());
            accountLog.setTradeType(4);
            accountLog.setHandler(handler);
            accountLog.setHouseOwner(house.getOwnerName());
            accountLog.setTradeMoney(houseCost.negate());
            accountLog.setTradeTime(stamp);
            accountLog.setSeq(stamp.getTime());
            accountLog.setRemark(record.toString());
            //余额变更
            house.setAccountBalance(newBalance);
            houseMapper.updateBalanceByCode(house);
            accountLogMapper.insert(accountLog);
        }


        return 0;
    }

    @Override
    @Transactional
    public int shareBack(Integer record,String handler) {
        int sum =0;
        Date stamp = new Date();
        RepairRecord repairRecord = recordMapper.selectByPrimaryKey(record);

        //查询余额变更记录
        List<AccountLog> accountLogs = accountLogMapper.selectByRecord(repairRecord);
        //批量回滚余额
        for(AccountLog accountLog:accountLogs){

            BigDecimal tradeMoney = accountLog.getTradeMoney().negate();//此时取反应为正数
            //即时账户余额
            BigDecimal balance = houseMapper.selectBalanceByCode(accountLog.getHouseCode());
            //新账户余额
            BigDecimal newBalance = balance.add(tradeMoney);
            House house = new House();
            house.setAccountBalance(newBalance);
            house.setCode(accountLog.getHouseCode());
            //更新余额信息
            sum += houseMapper.updateBalanceByCode(house);

            //插入变更记录
            accountLog.setId(null);
            accountLog.setTradeType(5);
            accountLog.setTradeMoney(tradeMoney);
            accountLog.setTradeTime(stamp);
            accountLog.setBalance(balance);
            accountLog.setSeq(stamp.getTime());
            accountLog.setHandler(handler);
            accountLog.setRemark(record.toString());
            sum += accountLogMapper.insert(accountLog);
        }

        //重置record状态
        repairRecord.setShareSeq(null);
        repairRecord.setState(0);
        repairRecord.setStamp(stamp);
        sum += recordMapper.stateChange(repairRecord);

        return sum;
    }

    public BigDecimal getRecordCost(Integer id){
        return recordMapper.recordCostSum(id);
    }

    @Override
    public int sumShareHouses(String paths) {
        //解析第一层路径，每个元素都是一个小区-房屋的层级关系
        String[] pathsArr = paths.split(",");
        int sum = 0;
        for(String path:pathsArr){
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = path.split("-");
            switch (ids.length-1){
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    sum += shareMapper.allHouseInResidentialArea(Integer.parseInt(ids[HouseTree.RESIDENTIALAREA_LEVEL]));
                    break;
                case HouseTree.BUILDING_LEVEL:
                    sum += shareMapper.allHouseInBuilding(Integer.parseInt(ids[HouseTree.BUILDING_LEVEL]));
                    break;
                case HouseTree.UNIT_LEVEL:
                    sum += shareMapper.allHouseInUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    break;
                case HouseTree.FLOOR_LEVEL:
                    House house = new House();
                    house.setUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    house.setFloor(ids[HouseTree.FLOOR_LEVEL]);
                    sum += shareMapper.allHouseInFloor(house);
                    break;
                case HouseTree.HOUSE_LEVEL:
                    sum += 1;
                    break;
            }
        }
        return sum;
    }

    @Override
    public BigDecimal sumShareArea(String paths) {
        String[] pathsArr = paths.split(",");
        BigDecimal sum = new BigDecimal(0f);
        for(String path:pathsArr){
            String[] ids = path.split("-");
            BigDecimal result;
            switch (ids.length-1){
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    result = shareMapper.allAreaInResidentialArea(Integer.parseInt(ids[HouseTree.RESIDENTIALAREA_LEVEL]));
                    sum = sum.add(result==null?new BigDecimal(0f):result);
                    break;
                case HouseTree.BUILDING_LEVEL:
                    result = shareMapper.allAreaInBuilding(Integer.parseInt(ids[HouseTree.BUILDING_LEVEL]));
                    sum = sum.add(result==null?new BigDecimal(0f):result);
                    break;
                case HouseTree.UNIT_LEVEL:
                    result = shareMapper.allAreaInUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    sum = sum.add(result==null?new BigDecimal(0f):result);
                    break;
                case HouseTree.FLOOR_LEVEL:
                    House house = new House();
                    house.setUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    house.setFloor(ids[HouseTree.FLOOR_LEVEL]);
                    result = shareMapper.allAreaInFloor(house);
                    sum = sum.add(result==null?new BigDecimal(0f):result);
                    break;
                case HouseTree.HOUSE_LEVEL:
                    result = houseMapper.selectSimpleOne(Integer.parseInt(ids[HouseTree.HOUSE_LEVEL])).getArea();
                    sum = sum.add(result==null?new BigDecimal(0f):result);
                    break;
            }
        }
        return sum;
    }

    @Override
    public Map<String, BigDecimal> shareBackInfo(Long seq) {
        return shareMapper.shareBackInfo(seq);
    }

}
