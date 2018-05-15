package com.qth.service.impl;

import com.qth.dao.ChargeBillMapper;
import com.qth.model.ChargeBill;
import com.qth.service.IChargeBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChargeBillService extends BaseService<ChargeBill> implements IChargeBillService{

    @Autowired
    ChargeBillMapper chargeBillMapper;

    @Override
    public List<ChargeBill> selectAll() {
        return chargeBillMapper.selectAll();
    }

    @Override
    public int insertChargeBill(ChargeBill chargeBill) {
        return chargeBillMapper.insert(chargeBill);
    }

    @Override
    public int updateChargeBill(ChargeBill chargeBill) {
        return chargeBillMapper.updateByPrimaryKey(chargeBill);
    }

    @Override
    public ChargeBill findChargeBillById(int id) {
        return chargeBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteChargeBillById(int id) {
        return chargeBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int countByHouse(Integer house) {
        return chargeBillMapper.countByHouse(house);
    }
}
