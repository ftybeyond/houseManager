package com.qth.util;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.*;

/**
 * by fty ,自定义对象工厂，解决默认工厂返回map类型无序
 */
public class MyObjectFactory extends DefaultObjectFactory {
    @Override
    protected Class<?> resolveInterface(Class<?> type) {
        Class classToCreate;
        if (type != List.class && type != Collection.class && type != Iterable.class) {
            if (type == Map.class) {
                classToCreate = LinkedHashMap.class;
            } else if (type == SortedSet.class) {
                classToCreate = TreeSet.class;
            } else if (type == Set.class) {
                classToCreate = HashSet.class;
            } else {
                classToCreate = type;
            }
        } else {
            classToCreate = ArrayList.class;
        }

        return classToCreate;
    }
}
