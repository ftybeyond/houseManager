package com.qth.service;

import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.common.Select2;
import com.qth.model.common.UpdateMap;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {

    public List<Select2> getSelect2Data(String table);

    public DataTableRspWrapper<T> selectDataTable2Rsp(T entity);

    public int updateByMap(UpdateMap map);

    public DataTableRspWrapper<T> selectDataTable2Rsp(T entity,Map<String,String> orders);

    public DataTableRspWrapper<T> selectDataTable2Rsp(T entity, Map<String,String> orders, Map<String,String> conditions);

}
