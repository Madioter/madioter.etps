package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.Industry;

import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public interface IndustryDao {

    public List<Industry> getAllIndustry();

    public int save(Industry industry);
}
