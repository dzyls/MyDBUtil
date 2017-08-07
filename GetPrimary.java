package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 17:06
 */

import com.xing.jdbc.PKey;

import java.lang.reflect.Field;

public class GetPrimary {


    public static boolean isPrimary(Field field){
        field.setAccessible(true);
        if (field.getDeclaredAnnotation(PKey.class).isPKey()){
            return true;
        }
        return false;
    }

    public static String getPrimaryName(Field []fields) throws PKeyNotFound {
        for (int i = 0; i < fields.length; i++) {
            if (isPrimary(fields[i])){
                return fields[i].getName();
            }
        }
        throw new PKeyNotFound("未发现主键");
    }




}
class PKeyNotFound extends Exception{
    public PKeyNotFound(String message) {
        super(message);
    }
}
