package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.EnterpriseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Repository
public interface EnterpriseDao {

    public List<EnterpriseVo> getEnterpriseByNameAndRegDate(@Param("name") String name, @Param("beginDate") Date beginDate,
                                                            @Param("endDate") Date endDate, @Param("startNum") int startNum,
                                                            @Param("pageSize") int pageSize);

    public int countEnterpriseByNameAndRegDate(@Param("name") String name, @Param("beginDate") Date beginDate,
                                               @Param("endDate") Date endDate);

    public int save(EnterpriseVo model);

    public void update(EnterpriseVo model);
}
