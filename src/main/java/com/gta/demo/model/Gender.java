package com.gta.demo.model;

import com.gta.demo.common.mybatis.IDBEnum;

/**
 * Created by yi.wang1 on 2015/4/25.
 */
public enum Gender implements IDBEnum {
    MALE("男", 0), FEMALE("女", 1);

    private String name;

    private int id;

    Gender(String name, int id) {
        setName(name);
        setId(id);
    }

    public static String getName(int id) {
        for (Gender c : Gender.values()) {
            if (c.getId() == id) {
                return c.getName();
            }
        }
        return null;
    }

    public static Gender get(int id) {
        for (Gender c : Gender.values()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}