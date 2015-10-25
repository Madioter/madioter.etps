package com.gta.demo.dao;

import com.gta.demo.model.OrgClass;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yi.wang1 on 2015/5/12.
 */
@Repository
public interface IOrgClassDao {

    @Select("select * from t_class c")
    public List<OrgClass> getAllOrgClass();

    @Select("select * from t_class s where s.id = #{id}")
    public OrgClass getOrgClassById();

}
