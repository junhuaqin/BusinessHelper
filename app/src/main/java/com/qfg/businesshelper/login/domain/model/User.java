package com.qfg.businesshelper.login.domain.model;

/**
 * Created by rbtq on 9/1/16.
 */
public class User {
    public int id;
    public String name;
    public String userName;
    public String password;
    public boolean active;
    public long createAt;
    public long lastLoginAt;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }

    public long getCreateAt() {
        return createAt;
    }

    public User setCreateAt(long createAt) {
        this.createAt = createAt;
        return this;
    }

    public long getLastLoginAt() {
        return lastLoginAt;
    }

    public User setLastLoginAt(long lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }
}
