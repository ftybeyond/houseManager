package com.fty.code;


import com.qth.dao.CompanyMapper;
import com.qth.model.Company;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.common.SelectDataTableMap;
import com.qth.service.ICompanyService;
import com.qth.util.BeanUtil;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"}) //加载配置文件
public class CommonTest {

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    SqlSessionFactory sessionFactory;

    @Autowired
    ICompanyService companyService;

    @Test
    @Transactional
    public void commonDao() {

//        Map map = new HashMap();
//        map.put("name" ,"联通");
//        Map<Integer, Map<String, String>> result = companyMapper.selectDataTable2(new SelectDataTableMap("company", map));
//        for(Integer key:result.keySet()){
//            List<ResultMapping> resultMappings = sessionFactory.getConfiguration().getResultMap("com.qth.dao.CompanyMapper.BaseResultMap").getPropertyResultMappings();
//            Company company = (Company) BeanUtil.convertByResultMap(result.get(key),resultMappings, Company.class);
//            System.out.println(company);
//        }
//        List<ResultMapping> resultMappings = sessionFactory.getConfiguration().getResultMap("com.qth.dao.CompanyMapper.BaseResultMap").getPropertyResultMappings();

//        List<Company> list = (List<Company>) BeanUtil.convertByResultMap(result, resultMappings, Company.class);
//        System.out.println(list);
//        companyMapper.getSelect2Data("region");
//        System.out.println(result);
//        companyMapper.updateByMap(new UpdateMap("region",new String[]{"name"},new String[]{"666"},20));
        Company company = new Company();
        company.setName("联通");
        DataTableRspWrapper<Company> companies =  companyService.selectDataTable2Rsp(company);
        System.out.println(companies);
    }
}