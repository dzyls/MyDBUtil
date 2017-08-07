package com.xing.jdbc;
/*
 *  @author dzyls
 *  @create 2017-08-04 15:34
 */

import java.sql.*;

public class Demo {


    public static void main(String[] args) {

        Connection connection = null;
        ResultSet set = null;
        Statement statement = null;
        PreparedStatement preparedStatement= null;


        try {
            Class.forName("com.mysql.jdbc.Driver");
           //DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 此方法不推荐使用,建立了两个Driver对象，浪费资源
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc?useSSL=true","xing","xing");//方式1

//            Properties properties = new Properties(); 方式2
//            properties.setProperty("user","xing");
//            properties.setProperty("password","xing");//key值是固定的
//            properties.setProperty("characterEncoding","utf8");
//            connection = DriverManager.getConnection("jdbc:mysql:///jdbc",properties);

              connection = DriverManager.getConnection("jdbc:mysql:///jdbc?useSSL=true&user=xing&password=xing&characterEncoding=utf8");


            preparedStatement= connection.prepareStatement("select * FROM users WHERE  1 =1 ");
            set =  preparedStatement.executeQuery();


            statement = connection.createStatement();

            int a =statement.executeUpdate("update USERs  SET name ='James' where id = 3");//可以进行增删改
            System.out.println(a);// 返回值为int代表的是修改数据的条数

            boolean b =statement.execute("UPDATE users SET name ='James' WHERE id=3");
            System.out.println(b);//如果查询的返回的是结果集，则返回true
            boolean c = statement.execute("SELECT * FROM users");
            System.out.println(c);//如果查询返回的不是结果集，则返回false



            while (set.next()) {

//                System.out.print(set.getString("name") + "\t");
//                System.out.print(set.getString("password") + "\t");
//                System.out.print(set.getString("email") + "\t");
//                System.out.print(set.getString("birthday") + "\t");
//                System.out.println();


                int id = set.getInt("id");
                String name = set.getString("name");
                System.out.println(id);
                System.out.println(name);


            }
//            Exception Throwable

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            if (set !=null) {
                try {
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



        }


    }
}
