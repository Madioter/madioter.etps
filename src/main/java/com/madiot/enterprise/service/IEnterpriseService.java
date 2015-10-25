package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.ErrorMessage;
import com.madiot.enterprise.model.EnterpriseVo;
import com.madiot.enterprise.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public interface IEnterpriseService {

    public int importEnterprise(List<EnterpriseVo> enterpriseList, ErrorMessage errorMessage, User loginUser);

    public List<EnterpriseVo> queryEnterprisePageByCondition(String name, Date beginDate, Date endDate, int rows, int page);

    public int countEnterpriseByCondition(String name, Date beginDate, Date endDate);

}
