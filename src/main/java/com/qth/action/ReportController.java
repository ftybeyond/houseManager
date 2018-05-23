package com.qth.action;

import com.qth.model.common.CommonRsp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/report")
@ResponseBody
public class ReportController extends BaseController {

    public CommonRsp tableByTreeMultiple(){
        return null;
    }
}
