package com.xing.jdbc;
/*
 *  @author 邢登辉
 *  17/8/4 14:56
 */

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

public class DBUtils {


    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(getDBProp());

    }

    public static String getDBProp() {
        Properties properties = new Properties();
        InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("prop/db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str = properties.getProperty("url")+"?useSSL=true&user="+properties.getProperty("user")+"&password="+properties.getProperty("password")
                ;
        //System.out.println(str);
        return str;
    }
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, SQLException {


        Users users = new Users(9,"张三","asdfa","rtwew",new Date(10000));
        try {
            addObject(users);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            //update(users);
//            deleteObject(users);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }


//        Object o = new Users();
//        Field [] fields = o.getClass().getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getName());
//        }
//        Users users = (Users) queryByID(Users.class,1);
//        System.out.println(users);
//        System.out.println(Users.class.getSimpleName());
//        LinkedList<Object>  users = queryByClass(Users.class);
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println(users.get(i));
//        }

    }
    public static LinkedList<Map<String,String>> queryUsers(Class cls) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set =null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM"+cls.getSimpleName());
            set = preparedStatement.executeQuery();
            LinkedList<Map<String,String>> list = new LinkedList<>();
            Map<String,String> map= null;
            ResultSetMetaData rsmd = set.getMetaData();
            int count = rsmd.getColumnCount();
            while (set.next()) {
                map = new HashMap<String,String>(cls.getDeclaredFields().length);
                for (int i = 0; i < count; i++) {
                     map.put(rsmd.getColumnName(i+1),set.getString(rsmd.getColumnName(i+1)));
                }
                list.add(map);

            }
            return list;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static boolean createTable(Class cls) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("CREATE TABLE "+cls.getSimpleName()+" ( ");

            Field[]fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                sql.append(fields[i].getName()+" ");
                String fs = fields.getClass().getSimpleName();
                if (fs.equals("Integer")){
                    sql.append(" int ");
                }else  if(fs.contains("Date")){
                    sql.append(" date ");
                }else if (fs.contains("Double")||fs.contains("Float")){
                    sql.append(" double ");
                }else{
                    sql.append(" varchar(200) ");
                }

                if (fields[i].getDeclaredAnnotation(PKey.class)!=null){
                    sql.append(" primary key ");
                }
                if (fields.length-1!=i){
                    sql.append(",");
                }
            }
            sql.append(")");
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.execute();

        }finally {

        }
        return  true;
    }



    public static LinkedList<Object> queryByClass(Class cls) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set =null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM "+cls.getSimpleName());
            set = preparedStatement.executeQuery();
            LinkedList<Object> list = new LinkedList<>();

            Field []fields = getTable(cls);
            while (set.next()) {
                Object o = getObjectIns(cls, set, fields);
                list.add(cls.cast(o));
            }
            return list;
        } finally {
           close(preparedStatement,connection);
        }
    }

    public static Object getObjectIns(Class cls, ResultSet set, Field[] fields) throws InstantiationException, IllegalAccessException, SQLException {
        Object o = cls.newInstance();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Class c = field.getType();
            field.setAccessible(true);
            field.set(cls.cast(o),set.getObject(field.getName(),c));
        }
        return o;
    }





    public static Object queryByID(Class cls, Integer id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set =null;
        try {
            Field []fields = getTable(cls);
            String PKname = null;
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getDeclaredAnnotation(PKey.class).isPKey()){
                    PKname = fields[i].getName();
                    break;
                }
            }
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM "+cls.getSimpleName()+" where "+PKname+"="+id);
            set = preparedStatement.executeQuery();
            while (set.next()) {
                Object o = getObjectIns(cls, set, fields);
                return o;
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }







    public static void updateObject( Object o) throws Exception {

        Method[] method = o.getClass().getMethods();
        Field [] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].getName().contains("id")) {
                if (fields[i].getDeclaredAnnotation(PKey.class).isPKey()) {

                    if (queryByID(o.getClass(), (Integer) fields[i].get(o)) != null) {
                        update(o);
                    }
                }
            }
        }
    }

    private static void update(Object o) throws SQLException, ClassNotFoundException, IllegalAccessException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Class cls = o.getClass();
        try {
            Field []fields = o.getClass().getDeclaredFields();

            StringBuilder sql = new StringBuilder("update "+cls.getSimpleName()+" set ");
            Object id = 0; Object PKname = null;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].getName().contains("id")){
                    id = fields[i].get(o);
                    PKname = fields[i].getName();
                    continue;
                }
                sql.append(fields[i].getName()+"='"+fields[i].get(o)+"' ");
                if (i!=fields.length-1){
                    sql.append(",");
                }

            }
            sql.append("where "+PKname +" = "+id);
            System.out.println(sql.toString());
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            int i = preparedStatement.executeUpdate();

            if (i==0){
                System.err.println("更新失败!数据库中没有此项");
            }else{
                System.out.println("更新成功");
            }


        } finally {
            close(preparedStatement,connection);
        }
    }



    private static void close(PreparedStatement preparedStatement,Connection connection) throws SQLException {
        preparedStatement.close();
        connection.close();
    }

    public static void deleteObject(Object o) throws Exception {

        Field [] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].getName().contains("id")){
                Integer id = (Integer) fields[i].get(o);
                if (queryByID(o.getClass(),id)!=null) {
                    delete(o.getClass(),id);
                }else{
                    System.err.println("错误");
                    return;
                }
            }
        }
    }

    private static void delete (Class cls , Integer id) throws SQLException, ClassNotFoundException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "delete from "+cls.getSimpleName()+" where id = "+id;
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            int i = preparedStatement.executeUpdate();

            if (i==0){
                System.err.println("删除失败!数据库中没有此项");
            }else{
                System.out.println("删除成功");
            }
        } finally {
            close(preparedStatement,connection);
        }
    }



    public static  void addObject(Object o) throws Exception {

        Field [] fields = o.getClass().getDeclaredFields();
        Class cls = o.getClass();
        StringBuilder sql =new StringBuilder("insert into "+cls.getSimpleName()+" (");
        StringBuilder sql2 = new StringBuilder(" values (");
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            sql.append(fields[i].getName());
            if (fields[i].getName().contains("id")){
                Integer id = (Integer) fields[i].get(o);
                if (queryByID(o.getClass(),id)!=null) {
                    System.err.println("数据库中已经有了此项");
                    return;
                }
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
        System.out.println(sql.append(sql2).toString());
        add(sql.toString());

    }


    private static void add(String sql) throws SQLException, ClassNotFoundException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            int i = preparedStatement.executeUpdate();

            if (i==0){
                System.err.println("插入失败!数据库中id重复");
            }else{
                System.out.println("插入成功");
            }
        } finally {
            close(preparedStatement,connection);
        }

    }




    public static Field[] getTable(Class cls){
        return cls.getDeclaredFields();
    }


}
