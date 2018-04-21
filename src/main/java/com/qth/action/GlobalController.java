package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.common.Select2;
import com.qth.service.ISelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局管控controller
 */
@Controller
public class GlobalController {

    /**
     * 全局异常处理
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonRsp exception(Exception e) {
        CommonRsp commonRsp = new CommonRsp();
        commonRsp.setSuccess(false);
        commonRsp.setResultCode("9999");
        commonRsp.setDescription("服务器内部错误" + e.getMessage());
        e.printStackTrace();
        return commonRsp;
    }

    @Autowired
    ISelectService selectService;

    /**
     * 通用select2组件数据请求服务
     *
     * @param table 数据字典表明
     * @return
     */
    @RequestMapping("/rest/select/{table}")
    @ResponseBody
    public List<Select2> getSelectData(@PathVariable String table) {
        return selectService.getAll(table);
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param region region
     * @return
     */
    @RequestMapping("/rest/selectStreetByRegion/{region}")
    @ResponseBody
    public List<Select2> getSelectStreetDataByRegion(@PathVariable Integer region) {
        return selectService.getStreetByRegion(region);
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param type type
     * @return
     */
    @RequestMapping("/rest/selectConfigSelect/{type}")
    @ResponseBody
    public List<Select2> getConfigSelect(@PathVariable String type) {
        return selectService.getConfigSelect(type);
    }

}