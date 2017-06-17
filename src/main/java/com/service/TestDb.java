package com.service;

/**
 * Created by geser on 17.06.17.
 */
public class TestDb {
    public static void main(String[] args) {
        UserService service = new UserService();
        System.out.println(service.isExist("admin", "admin"));
    }
}
