package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 17:50
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

    public static <E> void deleteObject(Class<E> cls,Integer id,ConnClass son) throws PKeyNotFound, SQLException, ClassNotFoundException {

        String str = GetPrimary.getPrimaryName(cls.getDeclaredFields());
        String sql = "delete from "+cls.getSimpleName()+" where "+str + " = "+id;
        execute(son,sql);
    }

    public static <E> void deleteObject(Class<E> cls,String str,String value,ConnClass son) throws SQLException, ClassNotFoundException {

        String sql = "delete from "+cls.getSimpleName()+" where "+str + " = '"+value+"'";
        execute(son, sql);
    }

    public static void execute(ConnClass son, String sql) throws SQLException, ClassNotFoundException {
        PreparedStatement statement =son.getPreparedStatement(sql);
        statement.executeUpdate();
    }


}
