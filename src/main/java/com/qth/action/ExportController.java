package com.qth.action;

import com.qth.model.AccountLog;
import com.qth.model.AccrualResult;
import com.qth.model.House;
import com.qth.model.dto.AccrualInfo;
import com.qth.model.dto.ReportForm;
import com.qth.service.IAccountLogService;
import com.qth.service.IAccrualResultService;
import com.qth.service.IShareService;
import com.qth.service.impl.HouseService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    IAccountLogService accountLogService;

    @RequestMapping(value = "/export/balanceBack")
    public void balanceBackDetail(ReportForm model, HttpServletResponse response, HttpServletRequest request) {
        String filename = "基金返还明细信息.xls";
        export(filename, model, response, request);
    }

    @RequestMapping(value = "/export/report")
    public void report(ReportForm model, HttpServletResponse response, HttpServletRequest request) {
        String filename = "统计报表.xls";
        export(filename, model, response, request);
    }

    @RequestMapping(value = "/export/summary")
    public void summary(ReportForm model, HttpServletResponse response, HttpServletRequest request){
        downloadRsp(request,response,"汇总统计.xls");

        HSSFWorkbook workbook = new HSSFWorkbook();                   // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet();                     // 创建工作表

        List<AccountLog> result = accountLogService.reportSummaryList(model);

        int rowNum = 0;

        String[] summaryMsgs = model.getSummaryMsg().split("-");
        for(String msg : summaryMsgs){
            HSSFRow title = sheet.createRow(rowNum++);
            HSSFCell  cellRowName = title.createCell(0);
            cellRowName.setCellType(CellType.STRING);                //设置列头单元格的数据类型
            cellRowName.setCellValue(msg.replace(",",""));
        }

        String[] header = new String[]{"小区","楼栋","单元","房号"};
        int columns = 0 ;

        HSSFRow headerRow = sheet.createRow(rowNum++);

        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("黑体");
        font2.setBold(true);//粗体显示
        font2.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font2);//选择需要用到的字体格式
        for (int i = 0; i < model.getSummaryGroup(); i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellType(CellType.STRING);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            headerCell.setCellValue(text);
            headerCell.setCellStyle(headerStyle);
        }
        columns = model.getSummaryGroup();
        if(model.getSummaryType()==3){
            Cell timeCell = headerRow.createCell(columns++);
            timeCell.setCellType(CellType.STRING);
            timeCell.setCellValue(new HSSFRichTextString("计息时间"));
            timeCell.setCellStyle(headerStyle);
        }
        Cell valueCell = headerRow.createCell(columns);
        valueCell.setCellType(CellType.STRING);
        valueCell.setCellValue(new HSSFRichTextString("金额(元)"));
        valueCell.setCellStyle(headerStyle);



        HSSFCell  cell = null;
        BigDecimal total = new BigDecimal(0f);
        for (AccountLog accountLog:result){
            HSSFRow row = sheet.createRow(rowNum++);

            switch (model.getSummaryGroup()) {
                case 4:
                    cell = row.createCell(3,CellType.STRING);//房号
                    cell.setCellValue(accountLog.getHouseName());
                case 3:
                    cell = row.createCell(2,CellType.STRING);//单元
                    cell.setCellValue(accountLog.getUnitName());
                case 2:
                    cell = row.createCell(1,CellType.STRING);//楼栋
                    cell.setCellValue(accountLog.getBuildingName());
                case 1:
                    cell = row.createCell(0,CellType.STRING);//小区
                    cell.setCellValue(accountLog.getResidentialAreaName());
            }
            if(model.getSummaryType()==3){
                Cell dateCell = row.createCell(columns-1);
                dateCell.setCellType(CellType.STRING);
                dateCell.setCellValue(accountLog.getRemark().substring(accountLog.getRemark().indexOf("至")+1));
            }
            Cell moneyCell = row.createCell(columns);
            moneyCell.setCellType(CellType.NUMERIC);
            moneyCell.setCellValue(accountLog.getSumResult().doubleValue());
            total = total.add(accountLog.getSumResult());
        }

        HSSFRow footer = sheet.createRow(rowNum);
        cell =  footer.createCell(0,CellType.STRING);
        cell.setCellValue("合计：");
        if (model.getSummaryGroup()>1) {
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, columns-1));
        }
        cell = footer.createCell(columns,CellType.NUMERIC);
        cell.setCellValue(total.doubleValue());
        //写出文件
        if(workbook !=null){
            try
            {
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void export(String filename, ReportForm model, HttpServletResponse response, HttpServletRequest request) {
        downloadRsp(request,response,filename);
        accountLogService.exportExcel(model, response, filename);

    }

    private void downloadRsp(HttpServletRequest request, HttpServletResponse response, String filename) {
        try {
            final String userAgent = request.getHeader("USER-AGENT");
            if (StringUtils.contains(userAgent, "MSIE")) {//IE浏览器
                filename = URLEncoder.encode(filename, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                filename = new String(filename.getBytes(), "ISO8859-1");
            } else {
                filename = URLEncoder.encode(filename, "UTF-8");//其他浏览器
            }
            response.addHeader("Content-Disposition", "attachment;filename="
                    + filename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    IShareService shareService;

    @RequestMapping(value = "/export/shareCheck")
    public void shareCheck(String paths,String names, Integer shareType, BigDecimal sumArea, Integer totalHouse, BigDecimal cost, Integer record,HttpServletRequest request,HttpServletResponse response) {
        List<House> details = new ArrayList<>();
        shareService.checkShare(paths, shareType, sumArea, totalHouse, cost, record,details);
        downloadRsp(request,response,"分摊明细.xls");

        HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet();                     // 创建工作表

        int row = 0;

        HSSFRow domain = sheet.createRow(row);
        Cell domainlabel = domain.createCell(0,CellType.STRING);
        domainlabel.setCellValue("分摊区域");
        Cell domainValue = domain.createCell(1,CellType.STRING);
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        domainValue.setCellValue(names);

        row++;
        HSSFRow detail = sheet.createRow(row);
        Cell detailMoneyLabel = detail.createCell(0,CellType.STRING);
        detailMoneyLabel.setCellValue("分摊金额");

        Cell detailMoneyValue = detail.createCell(1,CellType.NUMERIC);
        detailMoneyValue.setCellValue(cost.doubleValue());

        Cell detailHousesLabel = detail.createCell(2,CellType.STRING);
        detailHousesLabel.setCellValue("分摊户数");

        Cell detailHousesValue = detail.createCell(3,CellType.NUMERIC);
        detailHousesValue.setCellValue(totalHouse.intValue());

        row++;
        HSSFRow header = sheet.createRow(row);

        String[] headerTitle = new String[]{"小区","楼栋","单元","楼层","房号","面积","分摊金额","余额","差额"};
        for (int n = 0; n < headerTitle.length; n++) {
            HSSFCell cellRowName = header.createCell(n);
            cellRowName.setCellType(CellType.STRING);                //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(headerTitle[n]);
            cellRowName.setCellValue(text);
        }

        row++;
        HSSFCell  cell = null;
        for(House house:details){
            HSSFRow hssfRow = sheet.createRow(row);

            cell = hssfRow.createCell(0,CellType.STRING);//小区
            cell.setCellValue(house.getResidentialAreaName());

            cell = hssfRow.createCell(1,CellType.STRING);//楼栋
            cell.setCellValue(house.getBuildingName());

            cell = hssfRow.createCell(2,CellType.STRING);//单元
            cell.setCellValue(house.getUnitName());

            cell = hssfRow.createCell(3,CellType.STRING);//楼层
            cell.setCellValue(house.getFloor());

            cell = hssfRow.createCell(4,CellType.STRING);//房号
            cell.setCellValue(house.getName());

            cell = hssfRow.createCell(5,CellType.NUMERIC);//面积
            cell.setCellValue(house.getArea().doubleValue());

            cell = hssfRow.createCell(6,CellType.NUMERIC);//分摊金额
            cell.setCellValue(house.getShareMoney().doubleValue());

            cell = hssfRow.createCell(7,CellType.NUMERIC);//余额
            cell.setCellValue(house.getAccountBalance().doubleValue());

            cell = hssfRow.createCell(8,CellType.NUMERIC);//差额
            double r = house.getAccountBalance().subtract(house.getShareMoney()).doubleValue();
            if (r<0) {
                cell.setCellValue(r);
            }
            row++;
        }

        //写出文件
        if(workbook !=null){
            try
            {
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    IAccrualResultService accrualResultService;

    @RequestMapping(value = "/export/accrualResultSummary")
    public void accrualResult(HttpServletResponse response, HttpServletRequest request, AccrualResult accrualResult){
        List<AccrualInfo> list = accrualResultService.summaryResult(accrualResult);
        downloadRsp(request,response,"小区计息汇总(未登账).xls");

        HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet();                     // 创建工作表

        int row = 0;
        HSSFRow header = sheet.createRow(row);

        HSSFCell cellRowName = header.createCell(0);
        cellRowName.setCellType(CellType.STRING);                //设置列头单元格的数据类型
        cellRowName.setCellValue("小区");

        HSSFCell cellRowName1 = header.createCell(1);
        cellRowName1.setCellType(CellType.STRING);                //设置列头单元格的数据类型
        cellRowName1.setCellValue("计息截至日期");

        HSSFCell cellRowName2 = header.createCell(2);
        cellRowName2.setCellType(CellType.STRING);                //设置列头单元格的数据类型
        cellRowName2.setCellValue("计息金额");

        row++;
        HSSFCell  cell = null;
        BigDecimal total = new BigDecimal(0f);
        for(AccrualInfo accrualInfo:list){
            HSSFRow hssfRow = sheet.createRow(row++);

            cell = hssfRow.createCell(0,CellType.STRING);//小区
            cell.setCellValue(accrualInfo.getResidentialAreaName());

            cell = hssfRow.createCell(1,CellType.STRING);//楼栋
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cell.setCellValue(dateFormat.format(accrualInfo.getEndTime()));

            cell = hssfRow.createCell(2,CellType.NUMERIC);//单元
            cell.setCellValue(accrualInfo.getAccrualSum().doubleValue());
            total = total.add(accrualInfo.getAccrualSum());
        }

        HSSFRow footer = sheet.createRow(row);
        cell =  footer.createCell(0,CellType.STRING);
        cell.setCellValue("合计：");
        sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 1));
        cell = footer.createCell(2,CellType.NUMERIC);
        cell.setCellValue(total.doubleValue());

        //写出文件
        if(workbook !=null){
            try
            {
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
