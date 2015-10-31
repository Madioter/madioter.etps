package com.madiot.enterprise.controller;

import com.madiot.enterprise.common.CommonConstant;
import com.madiot.enterprise.common.excel.ExcelReader;
import com.madiot.enterprise.common.exception.ErrorMessage;
import com.madiot.enterprise.model.EnterpriseVo;
import com.madiot.enterprise.model.ResponseList;
import com.madiot.enterprise.model.ResponseVo;
import com.madiot.enterprise.model.User;
import com.madiot.enterprise.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private IEnterpriseService enterpriseService;

    @RequestMapping("/enterpriseList")
    public String list() {
        return "enterprise/enterpriseList";
    }

    @RequestMapping("/enterpriseListJson")
    @ResponseBody
    public ResponseList enterpriseListJson(String name, Long beginDateMis, Long endDateMis, int rows, int page) {
        ResponseList<EnterpriseVo> responseList = new ResponseList<EnterpriseVo>();
        Date beginDate = null;
        Date endDate = null;
        if (beginDateMis != null && beginDateMis > 0) {
            beginDate = new Date(beginDateMis);
        }
        if (endDateMis != null && endDateMis > 0) {
            endDate = new Date(endDateMis);
        }
        List<EnterpriseVo> enterpriseList = enterpriseService.queryEnterprisePageByCondition(name, beginDate, endDate, rows, page);
        int total = enterpriseService.countEnterpriseByCondition(name, beginDate, endDate);
        responseList.setRows(enterpriseList);
        responseList.setTotal(total);
        return responseList;
    }


    @RequestMapping("/excelImport")
    public ModelAndView excelImport(HttpSession session, @RequestParam MultipartFile[] excelFile) {
        ResponseVo result = new ResponseVo();
        ModelAndView modelAndView = new ModelAndView("forward:enterpriseList");
        modelAndView.addObject("result", result);
        User user = new User();
        user.setId(1);
            /*User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
            if (user == null) {
                result.setSuccess(false);
                result.setMessage(CommonConstant.NO_LOGIN);
            }*/
        ExcelReader reader = new ExcelReader();
        List<EnterpriseVo> enterpriseList;
        MultipartFile file = excelFile[0];
        InputStream stream = null;
        try {
            stream = file.getInputStream();
            enterpriseList = reader.excelToModel(EnterpriseVo.class, stream);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(CommonConstant.IMPORT_ERROR + e.getMessage());
            return modelAndView;
        } finally {
            //关闭流
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                result.setSuccess(false);
                result.setMessage(CommonConstant.IMPORT_ERROR + e.getMessage());
                return modelAndView;
            }
        }
        ErrorMessage errorMessage = new ErrorMessage();
        int total = enterpriseService.importEnterprise(enterpriseList, errorMessage, user);
        result.setSuccess(!errorMessage.haveError());
        result.setData(total);
        result.setMessage(errorMessage.toString());
        return modelAndView;
    }

}
