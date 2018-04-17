package com.qth.action;

import com.qth.model.Company;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("rest/company")
@ResponseBody
public class CompanyController extends BaseController{

    @Autowired
    ICompanyService companyService;

    /**
     * 区域表格数据请求
     * @param company
     * @return
     */
    @RequestMapping(path="table")
    public DataTableRspWrapper<Company> table(Company company){
        DataTableRspWrapper<Company> rspWrapper = companyService.selectDataTable2Rsp(company);
        //赋值绘制时序标识
        rspWrapper.setDraw(company.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     * @param Company
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(Company Company){
        int effect = companyService.insertCompany(Company);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(Company company){
        int effect = companyService.updateCompany(company);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        Company Company = companyService.findCompanyById(id);
        return data2Rsp(Company);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = companyService.deleteCompanyById(id);
        return dbEffect2Rsp(effect);
    }
}
