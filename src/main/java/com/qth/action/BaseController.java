package com.qth.action;

import com.qth.model.common.CommonRsp;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * by fty
 * controll基类，声明常用方法
 */
public class BaseController {

    /**
     * 根据数据更新行数返回json请求对象
     *
     * @param effect 对数据库影响的行数，通常为insert delete update返回的结果
     */
    public CommonRsp dbEffect2Rsp(int effect) {
        CommonRsp rsp = new CommonRsp();
        if (effect > 0) {
            rsp.setSuccess(true);
            rsp.setResultCode("0000");
            rsp.setDescription("操作成功");
        } else {
            rsp.setSuccess(false);
            rsp.setResultCode("1000");
            rsp.setDescription("数据库更新失败!");
        }
        return rsp;
    }

    public CommonRsp data2Rsp(Object data) {
        CommonRsp rsp = new CommonRsp(true, "0000", "查询成功");
        rsp.setData(data);
        return rsp;
    }

    public CommonRsp list2Rsp(List list) {
        CommonRsp rsp = new CommonRsp(true, "0000", "查询成功");
        rsp.setDataList(list);
        return rsp;
    }

    public CommonRsp description2Rsp(String description){
        CommonRsp rsp = new CommonRsp(true, "0000", description);
        return rsp;
    }


    /**
     * 全局异常处理
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonRsp exception(Exception e) {
        CommonRsp commonRsp = new CommonRsp();
        commonRsp.setSuccess(false);
        commonRsp.setResultCode("9999");
        commonRsp.setDescription("服务器内部错误：" + e.getMessage());
        e.printStackTrace();
        return commonRsp;
    }

    public String getHandler(HttpSession session){
        return "admin";
    }
}

