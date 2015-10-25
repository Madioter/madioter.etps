package com.gta.demo.dao;

import com.gta.demo.model.OrgClass;
import com.gta.demo.model.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yi.wang1 on 2015/5/12.
 */
@Repository
public interface IStudentDao {

    @Select("select * from t_student s where s.id = #{id}")
    @ResultMap("studentResult")
    public Student getStudentById(Integer id);

    /*@Select("select * from t_student s left join t_class c on s.class_id = c.id where s.name like '%' + #{name} + '%' " +
            "and c.name like '%' + #{className} + '%' limit #{startNum},#{pageSize} ")*/
    /*public List<Student> getStudentByNameAndClassName(@Param("name") String name, @Param("className") String className,
                                                      @Param("startNum") Integer startNum, @Param("pageSize") Integer pagsSize);*/
    public List<Student> getStudentByNameAndClassName(@Param("name") String name, @Param("className") String className,
                                                      @Param("startNum") Integer startNum, @Param("pageSize") Integer pagsSize);

    public int countStudentByNameAndClassName(@Param("name") String name, @Param("className") String className);

    public Integer checkNumberExist(@Param("id") Integer id, @Param("number") String number);

    public void saveStudent(Student stu);

    public void deleteStudent(int id);

    public void deleteStudentByBatch(List<Integer> ids);

    public void updateStudent(Student stu);
}
