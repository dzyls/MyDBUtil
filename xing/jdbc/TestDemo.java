package com.xing.jdbc;

/*
 *  @author dzyls
 *  @create 2017-08-04 19:37
 */

import org.junit.jupiter.api.Test;

import java.sql.Date;

public class TestDemo {


    @Test
    public void test(){
        Users users = new Users(12,"张三","asdfa","rtwew",new Date(10000));
        try {
            DBUtils.addObject(users);
            System.out.println(DBUtils.queryByID(users.getClass(), 12));
            users = new Users(12,"zzzz","aaaaa","rtwew",new Date(10000));
            DBUtils.updateObject(users);
            System.out.println(DBUtils.queryByID(users.getClass(), 12));
            DBUtils.deleteObject(users);
            System.out.println(DBUtils.queryByID(users.getClass(), 12));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
