package com.madiot.enterprise.service;

import com.madiot.enterprise.model.Industry;
import com.madiot.enterprise.model.RegAuth;

import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public interface ICodeService {

    public void clearCache();

    public List<Industry> getIndustryList();

    public List<RegAuth> getRegAuthList();

    public Industry getIndustryByName(String name);

    public RegAuth getRegAuthName(String name);

    public int saveIndustry(Industry industry);

    public int saveRegAuth(RegAuth regAuth);

}
