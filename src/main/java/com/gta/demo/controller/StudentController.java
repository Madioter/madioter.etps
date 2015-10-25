package com.gta.demo.controller;

import com.gta.demo.common.util.DateUtils;
import com.gta.demo.model.OrgClass;
import com.gta.demo.model.Student;
import com.gta.demo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yi.wang1 on 2015/4/24.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping("/studentList")
    public ModelAndView userList() {
        ModelAndView mv = new ModelAndView("student/studentList");
        return mv;
    }

    @RequestMapping("/studentListJson")
    @ResponseBody
    public Map<String, Object> userListJson(String name, String className, Integer page, Integer rows) {
        if (name == null) {
            name = "";
        }
        if (className == null) {
            className = "";
        }
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> studentList = studentService.getStudentByNameAndClassName(name, className, page, rows);
            int total = 0;
            if (studentList.size() < rows) {
                total = (page - 1) * rows + studentList.size();
            } else {
                total = studentService.countStudentByNameAndClassName(name, className);
            }
            int totalPage = 0;
            if (total % rows == 0) {
                totalPage = total / rows;
            } else {
                totalPage = total / rows + 1;
            }
            result.put("total", total);
            result.put("totalPages", totalPage);
            result.put("rows", studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("editStudent")
    public ModelAndView editStudent(Integer id) {
        ModelAndView mv = new ModelAndView("student/editStudent");
        if (id != null) {
            Student student = studentService.getStudentById(id);
            mv.addObject("student", student);
        }
        List<OrgClass> classes = studentService.getAllOrgClass();
        mv.addObject("classes", classes);
        return mv;
    }

    //http://localhost:8080/demo/student/checkNumberExist?number=100001
    /*@RequestMapping("/checkNumberExist")
    @ResponseBody
    public Map<String, Object> checkNumberExist(String number) {
        Boolean b = studentService.checkNumberExist(number);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("message", "返回结果为：" + b);
        return result;
    }*/

    @RequestMapping("/saveStudent")
    @ResponseBody
    public Map<String, Object> saveStudent(Student stu, String date) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (date != null) {
            stu.setBirthday(DateUtils.simpleDateFormat(date, "yyyy-MM-dd"));
        }
        String message = studentService.saveStudent(stu);
        result.put("message", message);
        return result;
    }

    @RequestMapping("/deleteStudent")
    @ResponseBody
    public Map<String, Object> deleteStudent(String ids, Integer id) {
        Map<String, Object> result = new HashMap<String, Object>();
        String message = "";
        if (ids != null) {
            message = studentService.deleteStudentByBatch(ids);
        }else{
            message = studentService.deleteStudentById(id);
        }
        result.put("message", message);
        return result;
    }

}
