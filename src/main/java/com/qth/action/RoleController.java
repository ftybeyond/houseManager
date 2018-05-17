package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.Role;
import com.qth.service.IRoleService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/role")
@ResponseBody
public class RoleController extends BaseController{

@Autowired
IRoleService roleService;

/**
* 区域表格数据请求
* @param role
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<Role> table(Role role){

    DataTableRspWrapper<Role> rspWrapper = roleService.selectDataTable2Rsp(role);
        //赋值绘制时序标识
        rspWrapper.setDraw(role.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param role
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Role role){
        int effect = roleService.insertRole(role);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Role role){
        int effect = roleService.updateRole(role);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        Role role = roleService.findRoleById(id);
        return data2Rsp(role);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = roleService.deleteRoleById(id);
        return dbEffect2Rsp(effect);
    }

}
