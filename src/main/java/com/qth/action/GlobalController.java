package com.qth.action;

import com.qth.util.CommonRsp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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

}