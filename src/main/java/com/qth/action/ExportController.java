package com.qth.action;

import com.qth.model.dto.ReportForm;
import com.qth.service.IAccountLogService;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.URLEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class ExportController {

    @Autowired
    IAccountLogService accountLogService;

    @RequestMapping(value = "/export/balanceBack")
    public void balanceBackDetail(ReportForm model, HttpServletResponse response, HttpServletRequest request){
        try {
            String filename = "基金返还明细信息.xls";
            final String userAgent = request.getHeader("USER-AGENT");
            if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
                filename = URLEncoder.encode(filename,"UTF-8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                filename = new String(filename.getBytes(), "ISO8859-1");
            }else{
                filename = URLEncoder.encode(filename,"UTF-8");//其他浏览器
            }
            response.addHeader("Content-Disposition", "attachment;filename="
                    +filename);
            accountLogService.exportExcel(model,response,filename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
