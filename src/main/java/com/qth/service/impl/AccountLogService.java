package com.qth.service.impl;

import com.qth.dao.AccountLogMapper;
import com.qth.model.AccountLog;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ReportForm;
import com.qth.service.IAccountLogService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class AccountLogService extends BaseService<AccountLog> implements IAccountLogService{

    @Autowired
    AccountLogMapper accountLogMapper;

    @Override
    public List<AccountLog> selectAll() {
        return accountLogMapper.selectAll();
    }

    @Override
    public int insertAccountLog(AccountLog accountLog) {
        return accountLogMapper.insert(accountLog);
    }

    @Override
    public int updateAccountLog(AccountLog accountLog) {
        return accountLogMapper.updateByPrimaryKey(accountLog);
    }

    @Override
    public AccountLog findAccountLogById(int id) {
        return accountLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteAccountLogById(int id) {
        return accountLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AccountLog> selectByHouseCode(String code) {
        return accountLogMapper.selectByHouseCode(code);
    }

    @Override
    public DataTableRspWrapper<AccountLog>  reportSummary(ReportForm reportForm) {
        DataTableRspWrapper<AccountLog> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setDraw(reportForm.getDraw());
        reportForm.setSqlAppend(HouseService.sqlAppend(reportForm.getPaths()));
        rspWrapper.setData(accountLogMapper.selectReportSummary(reportForm));
        return rspWrapper;
    }

    @Override
    public List<AccountLog> reportSummaryList(ReportForm reportForm){
        reportForm.setSqlAppend(HouseService.sqlAppend(reportForm.getPaths()));
        return accountLogMapper.selectReportSummary(reportForm);
    }

    @Override
    public DataTableRspWrapper<AccountLog> reportDetail(ReportForm reportForm) {
        DataTableRspWrapper<AccountLog> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setDraw(reportForm.getDraw());
        reportForm.setSqlAppend(HouseService.sqlAppend(reportForm.getPaths()));
        rspWrapper.setData(accountLogMapper.selectReportDetail(reportForm));
        rspWrapper.setRecordsTotal(accountLogMapper.selectReportDetailCount(reportForm));
        return rspWrapper;
    }

    @Override
    public void exportExcel(ReportForm reportForm, HttpServletResponse response, String title) {
        reportForm.setSqlAppend(HouseService.sqlAppend(reportForm.getPaths()));
        List<AccountLog> list = accountLogMapper.selectReportDetail(reportForm);


        HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet();                     // 创建工作表

        HSSFRow header = sheet.createRow(0);

        for (int n = 0; n < EXPORT_HEADER.length; n++) {
            HSSFCell  cellRowName = header.createCell(n);
            cellRowName.setCellType(CellType.STRING);                //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(EXPORT_HEADER[n]);
            cellRowName.setCellValue(text);
        }

        HSSFCell  cell = null;
        for(int i=0;i<list.size();i++){
            AccountLog accountLog = list.get(i);
            HSSFRow row = sheet.createRow(i+1);

            cell = row.createCell(0,CellType.STRING);//产业代码
            cell.setCellValue(accountLog.getHouseCode());

            cell = row.createCell(1,CellType.STRING);//业主姓名
            cell.setCellValue(accountLog.getHouseOwner());

            cell = row.createCell(2,CellType.STRING);//小区
            cell.setCellValue(accountLog.getResidentialAreaName());

            cell = row.createCell(3,CellType.STRING);//楼栋
            cell.setCellValue(accountLog.getBuildingName());

            cell = row.createCell(4,CellType.STRING);//单元
            cell.setCellValue(accountLog.getUnitName());

            cell = row.createCell(5,CellType.STRING);//楼层
            cell.setCellValue(accountLog.getHouseFloor());

            cell = row.createCell(6,CellType.STRING);//房号
            cell.setCellValue(accountLog.getHouseName());

            cell = row.createCell(7,CellType.NUMERIC);//面积
            cell.setCellValue(accountLog.getHouseArea());

            cell = row.createCell(8,CellType.STRING);//交易类型
            cell.setCellValue(AccountLog.TRADE_TYPE.get(accountLog.getTradeType()));

            cell = row.createCell(9,CellType.STRING);//交易时间
            if (accountLog.getTradeTime()!=null) {
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(accountLog.getTradeTime()));
            }else{
                cell.setCellValue("");
            }

            cell = row.createCell(10,CellType.STRING);//操作员
            cell.setCellValue(accountLog.getHandler());

            cell = row.createCell(11,CellType.NUMERIC);//交易金额
            if (accountLog.getTradeMoney()!=null) {
                cell.setCellValue(accountLog.getTradeMoney().doubleValue());
            }else{
                cell.setCellValue("");
            }
        }

        HSSFRow footer = sheet.createRow(list.size()+1);
        cell =  footer.createCell(0,CellType.STRING);
        cell.setCellValue("合计：");
        sheet.addMergedRegion(new CellRangeAddress(list.size()+1, list.size()+1, 0, 10));
        cell = footer.createCell(11,CellType.NUMERIC);
        cell.setCellValue(reportForm.getSupplement());

        //让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < 12; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if(colNum == 0){
                sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
            }else{
                sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
            }
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

    @Override
    public Double[] reportSum(ReportForm reportForm){
        reportForm.setSqlAppend(HouseService.sqlAppend(reportForm.getPaths()));
        Double[] result = new Double[2];
        result[0] = accountLogMapper.reportSum(reportForm);
        result[1] = accountLogMapper.reportTradeSum(reportForm);
        return result;
    }

}
