package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 18:20
 */

import java.lang.reflect.Field;

public class Add {


    public static void addObject(Object o,ConnClass son) throws Exception {
        Delete.execute(son,getStringBuilder(o, son).toString());
    }


    private static StringBuilder getStringBuilder(Object o,ConnClass son) throws Exception {
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder sql =new StringBuilder("insert into "+cls.getSimpleName()+" (");
        StringBuilder sql2 = new StringBuilder(" values (");
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            sql.append(fields[i].getName());
            if (fields[i].getName().contains("id")){
                Integer id = (Integer) fields[i].get(o);
                sql2.append(id);
            }else{
                sql2.append(fields[i].get(o)+"'");
            }
            if (i!=fields.length-1){
                sql.append(",");
                sql2.append(",'");
            }
        }
        sql.append(")");
        sql2.append(")");
        sql.append(sql2);
        return sql;
    }


}
