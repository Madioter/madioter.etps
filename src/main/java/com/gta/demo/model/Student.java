package com.gta.demo.model;

import java.util.Date;

/**
 * Created by yi.wang1 on 2015/4/24.
 */
public class Student {

    //ID
    private Integer id;

    //姓名
    private String name;

    //生日
    private Date birthday;

    //学号
    private String number;

    //性别
    private Gender gender;

    //所在班级
    private OrgClass orgClass;

    private String orgClassName;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public OrgClass getOrgClass() {
        return orgClass;
    }

    public void setOrgClass(OrgClass orgClass) {
        this.orgClass = orgClass;
    }

    public String getOrgClassName() {
        return orgClassName;
    }

    public void setOrgClassName(String orgClassName) {
        this.orgClassName = orgClassName;
    }
}
