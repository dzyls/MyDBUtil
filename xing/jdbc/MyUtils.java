package com.xing.jdbc;

/*
 *  @author dzyls
 *  @create 2017-08-05 10:12
 */

import java.sql.*;

public class MyUtils {

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("加载驱动错误\n"+e);
        }
    }
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static void InitMethod() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql:///jdbc?useSSL=true&user=xing&password=xing");
    }

    public static Connection getConnection() throws SQLException {
        if (connection==null){
            InitMethod();
        }
        return connection;
    }

    public static ResultSet excuteQuery(String sql) throws SQLException {
        preparedStatement = getConnection().prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public static boolean excute(String sql) throws SQLException {
        preparedStatement = getConnection().prepareStatement(sql);
        int temp = preparedStatement.executeUpdate();
        return temp>0?true:false;
    }
    private static void destroyMethod() throws SQLException {
        if (resultSet!=null){
            resultSet.close();
        }
        if (preparedStatement!=null){
            preparedStatement.close();
        }
        if (connection!=null){
            connection.close();
        }
    }

    public static Object allSql(String sql) throws SQLException {

        if (sql.contains("select")&&sql.contains("update")){
            preparedStatement = getConnection().prepareStatement(sql);
            return preparedStatement.execute();
        }else if (sql.contains("select")){
            return excuteQuery(sql);
        }else{
           return excute(sql);
        }
    }

    public static void main(String[] args) {
        try{
            System.out.println(allSql("insert into users values(12,'JK','password','email','1980-01-01')"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                destroyMethod();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
