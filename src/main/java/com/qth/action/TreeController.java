package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.common.ZTreeModel;
import com.qth.model.common.ZTreeNodeReq;
import com.qth.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/rest/tree")
public class TreeController extends BaseController{

    @Autowired
    IShareService shareService;

    @RequestMapping(value = "selectHouseTree")
    @ResponseBody
    public List<ZTreeModel> loadSelectHouseTree(ZTreeNodeReq req){
        List<ZTreeModel> list = shareService.loadTreeNodes(req);
        if(req.getChecked()!=null && req.getChecked()){
            for (ZTreeModel treeModel:list){
                treeModel.setChecked(true);
            }
        }
        return list;
    }
}
