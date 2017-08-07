package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 17:31
 */

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetObject {


    public static <E> E getObject(Class<E> cls, ResultSet set) throws IllegalAccessException, InstantiationException, SQLException {
        E o = cls.newInstance();
        Field [] fields = cls.getDeclaredFields();
        while (set.next()){
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                field.set(o,set.getObject(field.getName(),field.getType()));
            }
            break;
        }
        return o;
    }


}
