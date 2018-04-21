package com.qth.model.common;

import com.qth.util.BeanUtil;

import java.util.Map;

/**
 * by fty
 * 数据库表名与类名的映射关系，配置在spring中声明，默认不声明表名即类名首字母小写
 */
public class TableMapping {

    private Map<String,String> mapping;

    //通过model class 返回

    /**
     *
     * @param clazz model类型
     * @return 对应表名
     */
    public String getTableName(Class clazz){
        //
        if(mapping.containsKey(clazz.getSimpleName())){
            return mapping.get(clazz.getSimpleName());
        }else{
            return BeanUtil.toLowerCaseFirstOne(clazz.getSimpleName());
        }
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
