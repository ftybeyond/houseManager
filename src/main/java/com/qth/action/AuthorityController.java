package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.Authority;
import com.qth.service.IAuthorityService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/authority")
@ResponseBody
public class AuthorityController extends BaseController{

@Autowired
IAuthorityService authorityService;

/**
* 区域表格数据请求
* @param authority
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<Authority> table(Authority authority){

    DataTableRspWrapper<Authority> rspWrapper = authorityService.selectDataTable2Rsp(authority);
        //赋值绘制时序标识
        rspWrapper.setDraw(authority.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param authority
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Authority authority){
        int effect = authorityService.insertAuthority(authority);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Authority authority){
        int effect = authorityService.updateAuthority(authority);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        Authority authority = authorityService.findAuthorityById(id);
        return data2Rsp(authority);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = authorityService.deleteAuthorityById(id);
        return dbEffect2Rsp(effect);
    }

}
