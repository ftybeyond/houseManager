package com.qth.util;

import com.qth.model.common.SelectDataTableMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BeanUtil{

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass){
        if (map == null) {
            return null;
        }
        try {
            Object obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }

            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        if(obj == null){
            return null;
        }

        try {
            Map<String, Object> map = new HashMap<>();

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }

            return map;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过mybatis配置文件解析并把map对象转换成指定类型
     * @param map
     * @param resultMappings
     * @param beanClass
     * @return
     */
    public static List<?> convertByResultMap(Map<Integer,Map<String,Object>> map, List<ResultMapping> resultMappings,Class<?> beanClass){
        List list = new ArrayList(map.keySet().size());
        if (map.keySet().size()>0) {
            try {
                for(Integer key:map.keySet()){
                    Object obj = beanClass.newInstance();

                    for(ResultMapping resultMapping:resultMappings){
                        if(map.get(key).containsKey(resultMapping.getColumn())){
                            //如果匹配了reslutMap中的列名
                            Field field = obj.getClass().getDeclaredField(resultMapping.getProperty());
                            field.setAccessible(true);
                            Object setValue = map.get(key).get(resultMapping.getColumn());
                            if((setValue instanceof  String) &&(setValue==null||((String)setValue).trim().length()==0)){
                                continue;
                            }else{
                                //取列名对应字段的值
                                field.set(obj,setValue);
                            }

//                            if(resultMapping.getJdbcType().equals(JdbcType.INTEGER)){
//                                field.set(obj,Integer.parseInt(map.get(key).get(resultMapping.getColumn())));
//                            }else if(resultMapping.getJdbcType().equals(JdbcType.VARCHAR)){
//                                field.set(obj,map.get(key).get(resultMapping.getColumn()));
//                            }else if(resultMapping.getJdbcType().equals(JdbcType.NUMERIC)||resultMapping.getJdbcType().equals(JdbcType.DECIMAL)){
//                                field.set(obj,map.get(key).get(resultMapping.getColumn()));
//                            }else if(resultMapping.getJdbcType().equals(JdbcType.DATE)){
//                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                field.set(obj, dateFormat.parse(map.get(key).get(resultMapping.getColumn())));
//                            }else{
//                                //陆续补充
//                            }
                        }
                    }
                    list.add(obj);
                }
                return list;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static SelectDataTableMap searchBean2Map(Object entity,List<ResultMapping> resultMappings){
        if(entity == null){
            return null;
        }
        try {
            Map<String, Object> map = new HashMap<>();
            List<Field> list = new ArrayList<>();
            getAllField(entity.getClass(),list);
            for (Field field : list) {
                field.setAccessible(true);
                if(field.get(entity)!= null){
                    for(ResultMapping resultMapping:resultMappings){
                        if(field.get(entity).toString().trim().length()<1){
                            continue;
                        }
                        if(resultMapping.getProperty().equals(field.getName())){
                            //成员匹配数据库字段
                            map.put(resultMapping.getColumn(),field.get(entity));
                        }
                    }
                }

            }

            return new SelectDataTableMap("",null,map);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 输出所有成员，包括父类中的成员（通过递归实现）
     */
    public static void getAllField(Class clazz,List<Field> list) {
        Field[] fields = clazz.getDeclaredFields();
        if(fields != null && fields.length > 0) {
            for(Field field : fields) {
                list.add(field);
            }
        }
        Class superClazz = clazz.getSuperclass();
        if(superClazz != Object.class) { // 结束递归
            getAllField(superClazz,list); // 递归
        }
    }

    public static String toLowerCaseFirstOne(String word){
        if(Character.isLowerCase(word.charAt(0)))
            return word;
        else
            return (new StringBuilder()).append(Character.toLowerCase(word.charAt(0))).append(word.substring(1)).toString();
    }
}  