package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.ErrorMessage;
import com.madiot.enterprise.dao.EnterpriseDao;
import com.madiot.enterprise.model.EnterpriseVo;
import com.madiot.enterprise.model.Industry;
import com.madiot.enterprise.model.RegAuth;
import com.madiot.enterprise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Service
public class EnterpriseService implements IEnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private ICodeService codeService;

    public int importEnterprise(List<EnterpriseVo> enterpriseList, ErrorMessage errorMessage, User loginUser) {
        int successCount = 0;
        for (EnterpriseVo model : enterpriseList) {
            try {
                List<EnterpriseVo> enterpriseVoList = enterpriseDao.getEnterpriseByNameAndRegDate(model.getName(), null, null, 0, 1);
                if (enterpriseVoList.size() > 0) {
                    EnterpriseVo oldModel = enterpriseList.get(0);
                    if (oldModel.sameWith(model)) {
                        continue;
                    } else {
                        model.setId(oldModel.getId());
                    }
                }

                model.setUpdateUserId(loginUser.getId());
                model.setUpdateTime(new Date());

                Industry industry = codeService.getIndustryByName(model.getIndustry());
                if (industry != null) {
                    model.setIndustryCode(industry.getCode());
                } else {
                    industry = new Industry();
                    industry.setName(model.getIndustry());
                    int industryCode = codeService.saveIndustry(industry);
                    model.setIndustryCode(industryCode);
                }

                RegAuth regAuth = codeService.getRegAuthName(model.getRegAuth());
                if (regAuth != null) {
                    model.setIndustryCode(regAuth.getCode());
                } else {
                    regAuth = new RegAuth();
                    regAuth.setName(model.getRegAuth());
                    int regAuthCode = codeService.saveRegAuth(regAuth);
                    model.setRegAuthCode(regAuthCode);
                }

                if (model.getId() == null) {
                    enterpriseDao.save(model);
                } else {
                    enterpriseDao.update(model);
                }
                successCount ++;
            }catch (Exception e){
                errorMessage.addError(model.getName() + "数据保存失败：" + e.getMessage());
            }

        }
        return successCount;
    }
}
