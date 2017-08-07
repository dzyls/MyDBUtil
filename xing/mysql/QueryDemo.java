package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 17:03
 */

import com.xing.jdbc.PKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDemo {


    public static  <E>E queryByID(Class<E> cls, Integer id,ConnClass son) throws Exception {
        E e = null;
        ResultSet set =null;
        Field [] fields = cls.getDeclaredFields();
        String primary = GetPrimary.getPrimaryName(fields);
        PreparedStatement preparedStatement = son.getPreparedStatement("select * from "+cls.getSimpleName()+
                        " where "+primary +" = "+id);
        set = preparedStatement.executeQuery();
        e = GetObject.getObject(cls,set);

        return e;
    }



}
