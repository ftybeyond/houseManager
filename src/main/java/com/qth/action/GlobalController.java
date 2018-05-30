package com.qth.action;

import com.qth.model.*;
import com.qth.model.common.*;
import com.qth.model.dto.ChargeBillPrintInfo;
import com.qth.model.dto.ImportLog;
import com.qth.service.*;
import com.qth.util.DateUtils;
import com.qth.util.ImportUtil;
import com.qth.util.MD5;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 全局管控controller
 */
@Controller
public class GlobalController extends BaseController {

    @Autowired
    ISelectService selectService;

    @Autowired
    IChargeBillService chargeBillService;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    ICompanyService companyService;

    @Autowired
    IAuthorityService authorityService;

    @Autowired
    IHouseService houseService;

    @Value("${busi.coreCompany.id}")
    Integer company;

    @RequestMapping(value = "/forward/chargeBillPrint")
    public ModelAndView chargeBillPrint(Integer id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        if (id != null) {
            ChargeBillPrintInfo printInfo = chargeBillService.getPrintInfo(id);
            if (printInfo == null) {
                return modelAndView;
            }
            Company coreCompany = companyService.findCompanyById(company);
            printInfo.setHandler(getHandler(session));
            printInfo.setStamp(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
            printInfo.setCompanyAccount(coreCompany.getAccountNum());
            printInfo.setCompanyName(coreCompany.getName());
            modelAndView.addObject("printInfo", printInfo);
            modelAndView.setViewName("chargeBillPrint");
        }
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(User user, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        user.setPassword(MD5.EncoderByMd5(user.getPassword()));
        User loginUser = userService.checkPassword(user);
        if (loginUser != null) {
            Role role = roleService.findRoleById(loginUser.getRole());
            session.setAttribute("loginUser", loginUser);
            List<Authority> list;
            if (role.getAuthority().contains("\"0\"")) {
                list = authorityService.selectAll();
            } else {
                list = authorityService.selectByRole(role);
            }

            Map<String, List<Authority>> map = new LinkedHashMap<>();
            for (Authority authority : list) {
                if (!map.keySet().contains(authority.getModule())) {
                    List<Authority> authorityList = new ArrayList<>();
                    authorityList.add(authority);
                    map.put(authority.getModule(), authorityList);
                } else {
                    map.get(authority.getModule()).add(authority);
                }
            }
            session.setAttribute("authority", map);
            mv.setViewName("redirect:/page/main.action");
        } else {
            mv.addObject("message","密码错误!");
            mv.setViewName("index");
        }
        return mv;
    }

    @RequestMapping(value = "/forward/loginOut")
    public ModelAndView loginOut(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/page/index.action");
        session.invalidate();
        return mv;
    }

    @RequestMapping(value = "/rest/updatePassword")
    @ResponseBody
    public CommonRsp updatePassword(String oldPassword ,String newPassword,HttpSession session){
        User user = getLoginUser(session);
        User userCheck = new User();
        userCheck.setLoginName(user.getLoginName());
        userCheck.setPassword(MD5.EncoderByMd5(oldPassword));
        userCheck = userService.checkPassword(userCheck);
        if(userCheck ==null){
            return new CommonRsp(false,"8765","原始密码错误");
        }
        user.setPassword(MD5.EncoderByMd5(newPassword));
        int effect = userService.updatePassword(user);
        return dbEffect2Rsp(effect);
    }

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
     * @param street street
     * @return
     */
    @RequestMapping("/rest/selectResidentialAreaByStreet/{street}")
    @ResponseBody
    public List<Select2> getResidentialAreaDataByRegion(@PathVariable Integer street) {
        return selectService.getResidentialAreaByRegion(street);
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param residentialArea residentialArea
     * @return
     */
    @RequestMapping("/rest/selectBuildingByResidentialArea/{residentialArea}")
    @ResponseBody
    public List<Select2> getBuildingDataByResidentialArea(@PathVariable Integer residentialArea) {
        return selectService.getBuildingDataByResidentialArea(residentialArea);
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param building building
     * @return
     */
    @RequestMapping("/rest/selectUnitByBuilding/{building}")
    @ResponseBody
    public List<Select2> getUnitDataByBuilding(@PathVariable Integer building) {
        return selectService.getUnitDataByBuilding(building);
    }


    /**
     * 通用select2组件数据请求服务
     *
     * @param unit unit
     * @return
     */
    @RequestMapping("/rest/selectFloorByUnit/{unit}")
    @ResponseBody
    public List<Select2> getFloorDataByUnit(@PathVariable Integer unit) {
        return selectService.getFloorDataByUnit(unit);
    }

    /**
     * 通用select2组件数据请求服务
     *
     * @param house house
     * @return
     */
    @RequestMapping("/rest/selectHouseNameByUnitFloor/get")
    @ResponseBody
    public List<SelectIdstring> getHouseNameByUnitFloor(House house) {
        return selectService.getHouseNameDataByUnitFloor(house);
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

    @RequestMapping("/rest/upload/import")
    @ResponseBody
    public CommonRsp excelImport(@RequestParam("file") MultipartFile file, Integer residentialArea,HttpSession session) throws Exception {
        if(residentialArea == null){
            return new CommonRsp(false,"8801","请选择导入的小区信息!");
        }
        if (file != null) {
            if (file.getOriginalFilename().indexOf(".") > 0 && file.getOriginalFilename().split("\\.")[1].equals("xls")) {
                Workbook wb = new HSSFWorkbook(file.getInputStream());
                Sheet sheet = wb.getSheetAt(0);
                Map<String, ImportCacheNode> tree = new HashMap<>();
                Date stamp = new Date();
                int count = 0;
                for (int row = 1; row <= sheet.getLastRowNum(); row++) {
                    Row rowData = sheet.getRow(row);
                    ImportExcelRow importExcelRow = null;
                    ImportLog importLog = new ImportLog();
                    importExcelRow = ImportUtil.parseRow(rowData, tree, importLog);
                    if (importExcelRow == null) {
                        return new CommonRsp(false,"8802","第"+(row+1)+"行,第"+(importLog.getCol()+1)+"列数据格式不正确");
                    }
                    count++;
                }
                houseService.importByExcel(residentialArea,residentialArea,tree,getHandler(session),stamp);
                return new CommonRsp(true,"0000","成功导入"+  count + "条内容");
            } else {
                return new CommonRsp(false,"8802","不支持的文件类型");
            }
        } else {
            return new CommonRsp(false,"8804","文件不能为空!");
        }
    }
}