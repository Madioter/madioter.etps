package com.gta.demo.service;

import com.gta.demo.common.util.DateUtils;
import com.gta.demo.common.util.StringUtil;
import com.gta.demo.dao.IOrgClassDao;
import com.gta.demo.dao.IStudentDao;
import com.gta.demo.model.OrgClass;
import com.gta.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yi.wang1 on 2015/4/24.
 */
@Service
@Transactional
public class StudentService implements IStudentService {

    //@Autowired
    //private IStudentRepository studentRepository;

    @Autowired
    private IStudentDao studentDao;

    @Autowired
    private IOrgClassDao orgClassDao;

    /*@Autowired
    private IOrgClassRepository orgClassRepository;*/

    @Override
    public List<Map<String, Object>> getStudentByNameAndClassName(String name, String className, Integer page, Integer rows) {
        List<Student> studentList = studentDao.getStudentByNameAndClassName(name, className, (page - 1) * rows, rows);
        return getStudentShowList(studentList);
    }

    private List<Map<String, Object>> getStudentShowList(List<Student> studentList) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Student student : studentList) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("name", student.getName());
            item.put("id", student.getId());
            //item.put("className", student.getOrgClass().getName());
            item.put("className", student.getOrgClassName());
            item.put("birthday", DateUtils.simpleDateFormat(student.getBirthday(), "yyyy-MM-dd"));
            item.put("gender", student.getGender());
            item.put("number", student.getNumber());
            result.add(item);
        }
        return result;
    }

    @Override
    public int countStudentByNameAndClassName(String name, String className) {
        return studentDao.countStudentByNameAndClassName(name, className);
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentDao.getStudentById(id);
    }

    @Override
    public List<OrgClass> getAllOrgClass() {
        return orgClassDao.getAllOrgClass();
    }

    //@Override
    public String saveStudent(Student stu) {
        Integer value = studentDao.checkNumberExist(stu.getId(), stu.getNumber());
        if (value != null) {
            return "学号重复";
        }
        if (stu.getId() == null) {
            studentDao.saveStudent(stu);
        } else {
            studentDao.updateStudent(stu);
        }
        return "success";
    }

    @Override
    public String deleteStudentByBatch(String ids) {
        List<Integer> idList = StringUtil.idsToList(ids);
        studentDao.deleteStudentByBatch(idList);
        return "success";
    }

    @Override
    public String deleteStudentById(Integer id) {
        studentDao.deleteStudent(id);
        return "success";
    }
}
