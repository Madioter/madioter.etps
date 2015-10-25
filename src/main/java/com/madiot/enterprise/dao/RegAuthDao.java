package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.RegAuth;

import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public interface RegAuthDao {

    public List<RegAuth> getAllRegAuth();

    public int save(RegAuth regAuth);

}
