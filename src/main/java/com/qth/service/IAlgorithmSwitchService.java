package com.qth.service;

import com.qth.model.AlgorithmSwitch;

import java.util.List;

public interface IAlgorithmSwitchService extends IBaseService<AlgorithmSwitch>
{
    public List<AlgorithmSwitch> selectAll();

    public int insertAlgorithmSwitch(AlgorithmSwitch algorithmSwitch);

    public int updateAlgorithmSwitch(AlgorithmSwitch algorithmSwitch);

    public AlgorithmSwitch findAlgorithmSwitchById(int id);

    public int deleteAlgorithmSwitchById(int id);
}
