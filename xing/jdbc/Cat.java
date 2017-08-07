package com.xing.jdbc;

/*
 *  @author dzyls
 *  @create 2017-08-05 11:17
 */

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;

public class Cat {

    @PKey(isPKey = true)
    private Integer id;
    private String catname;
    private Date birth;


    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", catname='" + catname + '\'' +
                ", birth=" + birth +
                '}';
    }

    public static void main(String[] args) {

        try {
            Cat cat = new Cat();
            cat.id = 3;
            cat.catname = "Tom";
            cat.birth=new Date(0,10,9);
            DBUtils.addObject(cat);
            //System.out.println(DBUtils.queryByID(Cat.class, 1));
            //DBUtils.deleteObject(cat);
            LinkedList cats = DBUtils.queryByClass(Cat.class);
            for (Object o : cats) {
                System.out.println(o);
            }
            //DBUtils.createTable(Cat.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
