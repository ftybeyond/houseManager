package com.qth.service.impl;

import com.qth.dao.BaseMapper;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.common.Select2;
import com.qth.model.common.SelectDataTableMap;
import com.qth.model.common.UpdateMap;
import com.qth.service.IBaseService;
import com.qth.util.BeanUtil;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class BaseService<T> implements IBaseService<T> {

    protected Class<T> clazz;

    @Autowired
    private BaseMapper baseMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public BaseService() {
        // 得到被泛型的实体的class
        clazz = (Class<T>)getSuperClassGenricType(this.getClass(), 0);
    }

    @Override
    public List<Select2> getSelect2Data(String table) {
        return baseMapper.getSelect2Data(table);
    }

    @Override
    public DataTableRspWrapper<T> selectDataTable2Rsp(T entity) {
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        List<ResultMapping> resultMappings = sqlSessionFactory.getConfiguration().getResultMap("com.qth.dao." +clazz.getSimpleName()+"Mapper.BaseResultMap" ).getResultMappings();
        SelectDataTableMap map = BeanUtil.searchBean2Map(entity,resultMappings);
        List<T> data = (List<T>) BeanUtil.convertByResultMap(baseMapper.selectDataTable(map),resultMappings,clazz);
        rspWrapper.setData(data);
        rspWrapper.setRecordsTotal(baseMapper.selectDataTableCount(map));
        return rspWrapper;
    }

    @Override
    public int updateByMap(UpdateMap map) {
        return baseMapper.updateByMap(map);
    }

    /**
     * 获取本地泛型
     * @param clazz
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    private static Class<?> getSuperClassGenricType(Class<?> clazz, int index)
            throws IndexOutOfBoundsException
    {
        java.lang.reflect.Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType))
        {
            return Object.class;
        }

        java.lang.reflect.Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

        if (index >= params.length || index < 0)
        {
            return Object.class;
        }
        if (!(params[index] instanceof Class<?>))
        {
            return Object.class;
        }
        return (Class<?>)params[index];
    }

    private String getTableName(){
        return BeanUtil.toLowerCaseFirstOne(clazz.getName());
    }
}
