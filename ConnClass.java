package com.xing.mysql;

/*
 *  @author dzyls
 *  @create 2017-08-05 16:57
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnClass {

    private String path = "prop/db.properties";
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    public ConnClass(String path) throws SQLException, ClassNotFoundException {
        this.path = path;
        connection = getConnection();
    }

    public ConnClass() throws SQLException, ClassNotFoundException {
        connection = getConnection();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(getDBProp());
    }
    public PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException {

        if (connection==null) {
            connection = getConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        return preparedStatement;
    }

    private  String getDBProp() {
        Properties properties = new Properties();
        InputStream inputStream = ConnClass.class.getClassLoader().getResourceAsStream(path);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str = properties.getProperty("url")+"?useSSL=true&user="+
                properties.getProperty("user")+"&password="+properties.getProperty("password")
                ;
        return str;
    }
    public void close() throws SQLException {
        if (preparedStatement!=null) {
            preparedStatement.close();
        }
        if (connection!=null) {
            connection.close();
        }
    }

    public void release(Connection conn,Statement stat ,ResultSet set) throws SQLException {
        if(set!=null&&!set.isClosed()){
            set.close();
        }
        if (stat!=null&&!set.isClosed()) {
            stat.close();
        }
        if (conn!=null&&!conn.isClosed()) {
            conn.close();
        }
    }
}
