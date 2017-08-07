package com.xing.jdbc;

/*
 *  @author dzyls
 *  @create 2017-08-04 19:34
 */

import java.sql.Date;

public class Users {

    @PKey(isPKey = true)
    private Integer id;
    private String name;
    private String password;
    private String email;
    private Date birthday;

    public Users() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users(Integer id, String name, String password, String email, Date birthday) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
