package com.xing.jdbc;

/*
 *  @author dzyls
 *  @create 2017-08-05 10:46
 */

import org.junit.jupiter.api.Test;

public class TEst {


    @Test
    public void test(){


        Dog dog = new Dog(4,"Tom",100);
        try {

            //DBUtils.updateObject(dog);

            System.out.println(DBUtils.queryByID(Dog.class, 1));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


}
