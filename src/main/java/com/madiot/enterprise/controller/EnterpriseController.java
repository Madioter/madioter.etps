package com.madiot.enterprise.controller;

import com.madiot.enterprise.common.CommonConstant;
import com.madiot.enterprise.common.excel.ExcelReader;
import com.madiot.enterprise.common.exception.ErrorMessage;
import com.madiot.enterprise.model.EnterpriseVo;
import com.madiot.enterprise.model.ResponseVo;
import com.madiot.enterprise.model.User;
import com.madiot.enterprise.service.EnterpriseService;
import com.madiot.enterprise.service.IEnterpriseService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private IEnterpriseService enterpriseService;

    @RequestMapping("/enterpriseList")
    public String list(){
        return "enterprise/enterpriseList";
    }


    @RequestMapping("/excelImport")
    public void excelImport(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            ResponseVo result = new ResponseVo();
            User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
            if (user == null) {
                result.setSuccess(false);
                result.setMessage(CommonConstant.NO_LOGIN);
            }
            ExcelReader reader = new ExcelReader();
            List<EnterpriseVo> enterpriseList;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            response.setCharacterEncoding("utf-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            MultipartFile multipartFile = null;
            InputStream stream = null;
            for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
                multipartFile = set.getValue();//文件名
            }

            try {
                out = response.getWriter();
                stream = multipartFile.getInputStream();
                enterpriseList = reader.excelToModel(EnterpriseVo.class, stream);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage(CommonConstant.IMPORT_ERROR + e.getMessage());
                out.print(new JSONObject(result).toString());
                return;
            } finally {
                //关闭流
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                    result.setSuccess(false);
                    result.setMessage(CommonConstant.IMPORT_ERROR + e.getMessage());
                    out.print(new JSONObject(result).toString());
                    return;
                }
            }
            ErrorMessage errorMessage = new ErrorMessage();
            int total = enterpriseService.importEnterprise(enterpriseList, errorMessage, user);
            result.setSuccess(true);
            result.setData(total);
            result.setMessage(errorMessage.toString());
            out.print(new JSONObject(result).toString());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
