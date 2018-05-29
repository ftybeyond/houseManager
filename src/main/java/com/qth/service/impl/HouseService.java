package com.qth.service.impl;

import com.qth.dao.*;
import com.qth.model.*;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.common.ImportCacheNode;
import com.qth.model.dto.HouseTreeModel;
import com.qth.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class HouseService extends BaseService<House> implements IHouseService {

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    BuildingMapper buildingMapper;

    @Autowired
    UnitMapper unitMapper;

    @Autowired
    ChargeCriterionMapper chargeCriterionMapper;

    @Autowired
    AlgorithmSwitchMapper algorithmSwitchMapper;

    @Autowired
    AccountLogMapper accountLogMapper;

    @Value("${busi.algorithmSwitch.id}")
    Integer algorithmSwitch;

    @Override
    public DataTableRspWrapper<House> selectDataTable2Rsp(House house) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(houseMapper.selectDataTableCount(house));
        //设置数据集
        rspWrapper.setData(houseMapper.selectDataTable(house));
        return rspWrapper;
    }

    @Override
    public List<House> selectAll() {
        return houseMapper.selectAll();
    }

    @Override
    public int insertHouse(House house) {
        house.setAccountBalance(new BigDecimal(0f));
        house.setAccrualBalance(new BigDecimal(0f));
        house.setAccrualTime(new Date());
        return houseMapper.insert(house);
    }

    @Override
    public int updateHouse(House house) {
        return houseMapper.updateByPrimaryKey(house);
    }

    @Override
    public int updateOwnerInfo(House house) {
        return houseMapper.updateOwnerInfo(house);
    }

    @Override
    public House findHouseById(int id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteHouseById(int id) {
        return houseMapper.deleteByPrimaryKey(id);
    }

    public List<House> selectByTreeNode(HouseTreeModel model){
        return houseMapper.selectByTreeNode(model);
    }

    public int selectCountByTreeNode(HouseTreeModel model){
        return houseMapper.selectCountByTreeNode(model);
    }

    @Override
    public ChargeCriterion getChargeCriterionByHouse(Integer house ,Integer user) {
        Map map = new HashMap();
        map.put("houseId",house);
        map.put("userId",user);
        List<ChargeCriterion> list = chargeCriterionMapper.selectByHouse(map);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public AlgorithmSwitch getChargeType() {
        return algorithmSwitchMapper.selectByPrimaryKey(algorithmSwitch);
    }

    @Transactional
    @Override
    public int backBalance(Integer house,String handler) {
        House house1 = houseMapper.selectByPrimaryKey(house);
        AccountLog accountLog = new AccountLog();
        accountLog.setBalance(house1.getAccountBalance());
        accountLog.setHouseCode(house1.getCode());
        accountLog.setRemark("基金返还");
        accountLog.setTradeTime(new Date());
        accountLog.setTradeType(2);
        accountLog.setTradeMoney(house1.getAccountBalance().negate());
        accountLog.setHandler(handler);
        accountLog.setHouseOwner(house1.getOwnerName());
        accountLogMapper.insert(accountLog);
        house1.setAccountBalance(new BigDecimal(0f));
        return houseMapper.updateBalanceByCode(house1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int importByExcel(Integer residentialArea, Integer parentId, Map<String, ImportCacheNode> node,String handler,Date stamp) throws Exception {
        int effect = insertByTreeNode(residentialArea,parentId,node,handler,stamp);
        return effect;
    }

    private int insertByTreeNode(Integer residentialArea, Integer parentId, Map<String, ImportCacheNode> node,String handler,Date stamp) throws Exception {
        int count = 0;
        for(String name:node.keySet()){
            ImportCacheNode currentNode = node.get(name);
            if(currentNode.getLevel() == 0 && currentNode.getDbObj() == null){
                Building building = buildingMapper.findByName(parentId,currentNode.getKey());
                if(building==null){
                    building = new Building();
                    building.setName(currentNode.getKey());
                    building.setResidentialArea(residentialArea);
                    building.setUnits(currentNode.getChildren().keySet().size());
                    buildingMapper.insert(building);
                    currentNode.setNewInsert(true);
                    parentId = building.getId();
                }
                currentNode.setDbObj(building);
            }else if(currentNode.getLevel() == 1 && currentNode.getDbObj() == null){
                Unit unit =  unitMapper.findByName(parentId,currentNode.getKey());
                if(unit == null){
                    unit = new Unit();
                    unit.setBuilding(parentId);
                    unit.setName(currentNode.getKey());
                    unit.setTotalFloor(currentNode.getChildren().keySet().size());
                    unitMapper.insert(unit);
                    currentNode.setNewInsert(true);
                    parentId = unit.getId();
                }
                currentNode.setDbObj(unit);
            }else if(currentNode.getLevel() == 2){
                //楼层咱不处理
            }else if(currentNode.getLevel() == 3){
                Unit unit = (Unit)currentNode.getFather().getFather().getDbObj();
                String floor = currentNode.getFather().getKey();
                String houseName = currentNode.getImportExcelRow().getHouse();
                House house = houseMapper.findByUnitAndFloor(unit.getId(),floor,houseName);
                if(house != null){
                    throw new Exception("行：" + currentNode.getImportExcelRow().getRowNum() + "已存在");
                }else{
                    house = new House();
                    house.setAccrualBalance(currentNode.getImportExcelRow().getBalance());
                    house.setAccountBalance(currentNode.getImportExcelRow().getBalance());
                    house.setAccrualTime(currentNode.getImportExcelRow().getAccrualTime());
                    Building building = (Building)currentNode.getFather().getFather().getFather().getDbObj();

                    house.setName(houseName);
                    house.setUnit(unit.getId());
                    house.setUnitPrice(currentNode.getImportExcelRow().getUnitPrice());
                    house.setCode(residentialArea.toString() + building.getId() + unit.getId()+house.getName());
                    house.setFloor(floor);
                    house.setOwnerName(currentNode.getImportExcelRow().getOwnerName());
                    house.setOwnerPsptid(currentNode.getImportExcelRow().getOwnerLicense());
                    house.setOwnerTel(currentNode.getImportExcelRow().getOwnerTel());
                    house.setArea(currentNode.getImportExcelRow().getArea());
                    house.setHasElevator(currentNode.getImportExcelRow().getElevator());
                    house.setNature(currentNode.getImportExcelRow().getNature());
                    house.setType(currentNode.getImportExcelRow().getType());
                    currentNode.setNewInsert(true);
                    count ++;

                    houseMapper.insert(house);
                    AccountLog accountLog = new AccountLog();
                    accountLog.setHouseCode(house.getCode());
                    accountLog.setHouseOwner(house.getOwnerName());
                    accountLog.setTradeMoney(house.getAccountBalance());
                    accountLog.setTradeTime(stamp);
                    accountLog.setTradeType(0);
                    accountLog.setRemark("excel导入");
                    accountLog.setBalance(new BigDecimal(0f));
                    accountLog.setHandler(handler);
                    accountLog.setSeq(stamp.getTime());
                    accountLogMapper.insert(accountLog);

                }
            }
            //递归
            insertByTreeNode(residentialArea,parentId,currentNode.getChildren(),handler,stamp);
        }
        return count;
    }

    public List<House> selectByTreePath(String paths) {
        //解析第一层路径，每个元素都是一个小区-房屋的层级关系
        String[] pathsArr = paths.split(",");
        List<House> list = new ArrayList<>();
        for (String path : pathsArr) {
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = path.split("-");
            switch (ids.length - 1) {
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    //指定小区下所有房屋信息
                    list.addAll(houseMapper.allHousesInResidentialArea(Integer.parseInt(ids[HouseTree.RESIDENTIALAREA_LEVEL])));
                    break;
                case HouseTree.BUILDING_LEVEL:
                    list.addAll(houseMapper.allHousesInBuilding(Integer.parseInt(ids[HouseTree.BUILDING_LEVEL])));
                    break;
                case HouseTree.UNIT_LEVEL:
                    list.addAll(houseMapper.allHousesInUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL])));
                    break;
                case HouseTree.FLOOR_LEVEL:
                    House house = new House();
                    house.setUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    house.setFloor(ids[HouseTree.FLOOR_LEVEL]);
                    list.addAll(houseMapper.allHousesInFloor(house));
                    break;
                case HouseTree.HOUSE_LEVEL:
                    list.add(houseMapper.selectSimpleOne(Integer.parseInt(ids[HouseTree.HOUSE_LEVEL])));
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    public Date getLastAccrual(String paths){
        //解析第一层路径，每个元素都是一个小区-房屋的层级关系
        String[] residentialAreas = paths.split(",");
        Date lastDate=null;
        for (String residentialAreaPath : residentialAreas) {
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = residentialAreaPath.split("-");
            Date residentialAreaLast = null;
            switch (ids.length - 1) {
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    //指定小区下所有房屋信息
                    residentialAreaLast = houseMapper.lastAccrualInResidentialArea(Integer.parseInt(ids[HouseTree.RESIDENTIALAREA_LEVEL]));
                    break;
                case HouseTree.BUILDING_LEVEL:
                    residentialAreaLast = houseMapper.lastAccrualInBuilding(Integer.parseInt(ids[HouseTree.BUILDING_LEVEL]));
                    break;
                case HouseTree.UNIT_LEVEL:
                    residentialAreaLast = houseMapper.lastAccrualInUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    break;
                case HouseTree.FLOOR_LEVEL:
                    House house = new House();
                    house.setUnit(Integer.parseInt(ids[HouseTree.UNIT_LEVEL]));
                    house.setFloor(ids[HouseTree.FLOOR_LEVEL]);
                    residentialAreaLast = houseMapper.lastAccrualInFloor(house);
                    break;
                case HouseTree.HOUSE_LEVEL:
                    residentialAreaLast = houseMapper.selectSimpleOne(Integer.parseInt(ids[HouseTree.HOUSE_LEVEL])).getAccrualTime();
                    break;
                default:
                    break;
            }
            if(lastDate==null){
                lastDate = residentialAreaLast;
            }else{
                if(residentialAreaLast!=null){
                    lastDate = lastDate.getTime()>residentialAreaLast.getTime()?lastDate:residentialAreaLast;
                }
            }
        }
        return lastDate;
    }


    private int insertByTreeNode(Integer parentId,ImportCacheNode node){

        return 0;
    }
}
