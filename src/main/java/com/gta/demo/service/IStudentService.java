package com.gta.demo.service;

import com.gta.demo.model.OrgClass;
import com.gta.demo.model.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by yi.wang1 on 2015/4/24.
 */
public interface IStudentService {
    public List<Map<String, Object>> getStudentByNameAndClassName(String name, String className, Integer page, Integer rows);

    public int countStudentByNameAndClassName(String name, String className);

    public Student getStudentById(Integer id);

    public List<OrgClass> getAllOrgClass();

    public String saveStudent(Student stu);

    public String deleteStudentByBatch(String ids);

    public String deleteStudentById(Integer id);
}
