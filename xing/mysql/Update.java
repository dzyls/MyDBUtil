package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 18:05
 */

import com.xing.jdbc.PKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.xing.jdbc.DBUtils.queryByID;

public class Update {

    public static <E> void updateObject(Object o,ConnClass son) throws PKeyNotFound, SQLException, ClassNotFoundException, IllegalAccessException {
        String sql = getStringBuilder(o).toString();
        Delete.execute(son,sql);
    }
    private static StringBuilder getStringBuilder(Object o) throws IllegalAccessException {
        Class cls = o.getClass();
        Field [] fields = cls.getDeclaredFields();
        String PKname = null;
        StringBuilder sql = new StringBuilder("update "+cls.getSimpleName()+" set ");
        Object id = 0;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].getName().contains("id")){
                if (GetPrimary.isPrimary(fields[i])){
                    id = fields[i].get(o);
                    PKname = fields[i].getName();
                }
                continue;
            }
            sql.append(fields[i].getName()+"='"+fields[i].get(o)+"' ");
            if (i!=fields.length-1){
                sql.append(",");
            }
        }
        sql.append("where "+PKname +" = "+id);
        return sql;
    }
}
