package com.madiot.enterprise.service;

import com.madiot.enterprise.dao.IndustryDao;
import com.madiot.enterprise.dao.RegAuthDao;
import com.madiot.enterprise.model.Industry;
import com.madiot.enterprise.model.RegAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Service
public class CodeService implements ICodeService {

    @Autowired
    private IndustryDao industryDao;

    @Autowired
    private RegAuthDao regAuthDao;

    private List<Industry> industryList;

    private List<RegAuth> regAuthList;

    public void clearCache() {
        if (industryList != null) {
            industryList = null;
        }
        if (regAuthList != null) {
            regAuthList = null;
        }
    }

    public List<Industry> getIndustryList() {
        if (industryList == null) {
            industryList = industryDao.getAllIndustry();
        }
        return industryList;
    }

    public List<RegAuth> getRegAuthList() {
        if (regAuthList == null) {
            regAuthList = regAuthDao.getAllRegAuth();
        }
        return regAuthList;
    }

    @Override
    public Industry getIndustryByName(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        List<Industry> industryList = getIndustryList();
        for (int i = 0; i < industryList.size(); i++) {
            if (industryList.get(i).getName().equals(name)) {
                return industryList.get(i);
            }
        }
        return null;
    }

    @Override
    public RegAuth getRegAuthName(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        List<RegAuth> regAuthList = getRegAuthList();
        for (int i = 0; i < regAuthList.size(); i++) {
            if (regAuthList.get(i).getName().equals(name)) {
                return regAuthList.get(i);
            }
        }
        return null;
    }

    @Override
    public int saveIndustry(Industry industry) {
        industryDao.save(industry);
        industryList.add(industry);
        return industry.getCode();
    }

    @Override
    public int saveRegAuth(RegAuth regAuth) {
        regAuthDao.save(regAuth);
        regAuthList.add(regAuth);
        return regAuth.getCode();
    }
}
