package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.User;
import com.qth.service.IUserService;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/user")
@ResponseBody
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @Value("${busi.init.password}")
    private String initPassword;

    /**
     * 区域表格数据请求
     *
     * @param user
     * @return
     */
    @RequestMapping(path = "table")
    public DataTableRspWrapper<User> table(User user) {

        DataTableRspWrapper<User> rspWrapper = userService.selectDataTable2Rsp(user);
        //赋值绘制时序标识
        rspWrapper.setDraw(user.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(User user) {
        user.setPassword(MD5.EncoderByMd5(initPassword));
        int effect = userService.insertUser(user);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(User user) {
        int effect = userService.updateUser(user);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id) {
        User user = userService.findUserById(id);
        return data2Rsp(user);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id) {
        int effect = userService.deleteUserById(id);
        return dbEffect2Rsp(effect);
    }

}
