package com.polytech.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

/**
 * Created by dev on 4/11/17.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String idCommunaute;

    @Column(name = "enabled")
    private int enable;

    public User(){

    }

    public User(String username, String password, int enable) {
        this.username = username;
        this.password = password;
        this.enable = enable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return getUsername();
    }


    public String getIdCommunaute() {
        return idCommunaute;
    }

    public void setIdCommunaute(String idCommunaute) {
        this.idCommunaute = idCommunaute;
    }
}
