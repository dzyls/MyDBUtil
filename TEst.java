package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 17:43
 */

import com.xing.jdbc.Dog;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TEst {

    @Test
    public void test(){
        ConnClass son = null;
        try {
             son = new ConnClass();
            Dog dog = new Dog(3,"zhang",1000);
//          Update.updateObject(dog,son);
            Add.addObject(dog,son);
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                son.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


}
