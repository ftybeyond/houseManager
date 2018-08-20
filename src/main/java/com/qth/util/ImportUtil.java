package com.qth.util;

import com.qth.model.common.ImportExcelRow;
import com.qth.model.common.ImportCacheNode;
import com.qth.model.dto.ImportLog;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImportUtil {

    public static final Integer RESIDENTIAL_COLUMN_INDEX = 0;

    public static final Integer BUILDING_COLUMN_INDEX = 1;

    public static final Integer UNIT_COLUMN_INDEX = 2;

    public static final Integer FLOOR_COLUMN_INDEX = 3;

    public static final Integer HOUSE_COLUMN_INDEX = 4;

    public static final Integer AREA_COLUMN_INDEX = 5;

    public static final Integer PRICE_COLUMN_INDEX = 6;

    public static final Integer TYPE_COLUMN_INDEX = 7;

    public static final Integer ELEVATOR_COLUMN_INDEX = 8;

    public static final Integer NATURE_COLUMN_INDEX = 9;

    public static final Integer OWNER_NAME_COLUMN_INDEX = 10;

    public static final Integer OWNER_TEL_COLUMN_INDEX = 11;

    public static final Integer OWNER_LICENCE_COLUMN_INDEX = 12;

    public static final Integer BALANCE_COLUMN_INDEX = 13;

    public static final Integer ACCOUNT_TIME_COLUMN_INDEX = 14;

    public static final Integer ACCRUAL_TIME_COLUMN_INDEX = 15;

    public static final Integer PATCH_CHARGE_COLUMN_INDEX = 16;

    public static final Integer INVOICE_COLUMN_INDEX = 17;

    public static final Integer RESIDENTIAL_LEVEL = 0;

    public static final Integer BUILDING_LEVEL = 1;

    public static final Integer UNIT_LEVEL=2;

    public static final Integer FLOOR_LEVEL=3;

    public static final Integer HOUSE_LEVEL=4;

    //基础数据映射
    private static Map<String, Integer> houseTypeMap = new HashMap<>();
    private static Map<String, Integer> houseNatureMap = new HashMap<>();
    private static Map<String, Integer> elevatorMap = new HashMap<>();
    private static Map<String, Integer> patchChargeMap = new HashMap<>();

    static {
        houseTypeMap.put("住宅", 1);
        houseTypeMap.put("商服", 2);
        houseTypeMap.put("车库", 3);

        houseNatureMap.put("商品房", 1);
        houseNatureMap.put("已购公用住房", 2);
        houseNatureMap.put("旧有已购住房", 3);
        houseNatureMap.put("旧有住房", 4);

        elevatorMap.put("无电梯", 0);
        elevatorMap.put("有电梯", 1);

        patchChargeMap.put("否", 0);
        patchChargeMap.put("是", 1);
    }

    public static ImportExcelRow parseRow(Row row, Map<String, ImportCacheNode> cache, ImportLog importLog) {
        ImportExcelRow importExcelRow = new ImportExcelRow();
        importExcelRow.setRowNum(row.getRowNum() + 1);
        try {
            //关联数据校验
            importLog.setCol(TYPE_COLUMN_INDEX);
            Cell handlerCell = row.getCell(TYPE_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            if (houseTypeMap.containsKey(handlerCell.getStringCellValue())) {
                importExcelRow.setType(houseTypeMap.get(handlerCell.getStringCellValue()));
            } else {
                return null;
            }

            importLog.setCol(NATURE_COLUMN_INDEX);
            handlerCell = row.getCell(NATURE_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            if (houseNatureMap.containsKey(handlerCell.getStringCellValue())) {
                importExcelRow.setNature(houseNatureMap.get(handlerCell.getStringCellValue()));
            } else {
                return null;
            }

            importLog.setCol(ELEVATOR_COLUMN_INDEX);
            handlerCell = row.getCell(ELEVATOR_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            if (elevatorMap.containsKey(handlerCell.getStringCellValue())) {
                importExcelRow.setElevator(elevatorMap.get(handlerCell.getStringCellValue()));
            } else {
                return null;
            }

            importLog.setCol(PATCH_CHARGE_COLUMN_INDEX);
            handlerCell = row.getCell(PATCH_CHARGE_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            if (patchChargeMap.containsKey(handlerCell.getStringCellValue())) {
                importExcelRow.setPatchCharge(patchChargeMap.get(handlerCell.getStringCellValue()));
            } else {
                return null;
            }

            importLog.setCol(RESIDENTIAL_COLUMN_INDEX);
            handlerCell = row.getCell(RESIDENTIAL_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            String residentialArea = handlerCell.getStringCellValue();

            importLog.setCol(BUILDING_COLUMN_INDEX);
            handlerCell = row.getCell(BUILDING_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            String building = handlerCell.getStringCellValue();

            importLog.setCol(UNIT_COLUMN_INDEX);
            handlerCell = row.getCell(UNIT_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            String unit = handlerCell.getStringCellValue();

            importLog.setCol(FLOOR_COLUMN_INDEX);
            handlerCell = row.getCell(FLOOR_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            String floor = handlerCell.getStringCellValue();

            importLog.setCol(HOUSE_COLUMN_INDEX);
            handlerCell = row.getCell(HOUSE_COLUMN_INDEX);
            handlerCell.setCellType(CellType.STRING);
            String house = handlerCell.getStringCellValue();
            String[] params = new String[]{residentialArea,building, unit, floor, house};
            genTree(cache, importExcelRow, params, 0);
            importExcelRow.setResidentialArea(residentialArea);
            importExcelRow.setBuilding(building);
            importExcelRow.setUnit(unit);
            importExcelRow.setFloor(floor);
            importExcelRow.setHouse(house);

            importLog.setCol(AREA_COLUMN_INDEX);
            handlerCell = row.getCell(AREA_COLUMN_INDEX);
            handlerCell.setCellType(CellType.NUMERIC);
            importExcelRow.setArea(new BigDecimal(row.getCell(AREA_COLUMN_INDEX).getNumericCellValue()));

            importLog.setCol(PRICE_COLUMN_INDEX);
            handlerCell = row.getCell(PRICE_COLUMN_INDEX);
            handlerCell.setCellType(CellType.NUMERIC);
            importExcelRow.setUnitPrice(new BigDecimal(row.getCell(PRICE_COLUMN_INDEX).getNumericCellValue()));

            importLog.setCol(OWNER_NAME_COLUMN_INDEX);
            handlerCell = row.getCell(OWNER_NAME_COLUMN_INDEX);
            if(handlerCell!=null&&handlerCell.getStringCellValue().trim().length()>0) {
                handlerCell.setCellType(CellType.STRING);
                importExcelRow.setOwnerName(handlerCell.getStringCellValue());
            }

            importLog.setCol(OWNER_TEL_COLUMN_INDEX);
            handlerCell = row.getCell(OWNER_TEL_COLUMN_INDEX);
            if(handlerCell!=null&&handlerCell.getStringCellValue().trim().length()>0) {
                handlerCell.setCellType(CellType.STRING);
                importExcelRow.setOwnerTel(handlerCell.getStringCellValue());
            }

            importLog.setCol(OWNER_LICENCE_COLUMN_INDEX);
            handlerCell = row.getCell(OWNER_LICENCE_COLUMN_INDEX);
            if(handlerCell!=null&&handlerCell.getStringCellValue().trim().length()>0) {
                handlerCell.setCellType(CellType.STRING);
                importExcelRow.setOwnerLicense(handlerCell.getStringCellValue());
            }

            importLog.setCol(ACCOUNT_TIME_COLUMN_INDEX);
            handlerCell = row.getCell(ACCOUNT_TIME_COLUMN_INDEX);
            if(handlerCell!=null&&handlerCell.getStringCellValue().trim().length()>0){
                handlerCell.setCellType(CellType.STRING);
                importExcelRow.setAccountTime(DateUtils.parseDate(handlerCell.getStringCellValue(), "yyyy-MM-dd"));
            }

            importLog.setCol(ACCRUAL_TIME_COLUMN_INDEX);
            handlerCell = row.getCell(ACCRUAL_TIME_COLUMN_INDEX);
            if(handlerCell!=null&&handlerCell.getStringCellValue().trim().length()>0){
                handlerCell.setCellType(CellType.STRING);
                importExcelRow.setAccrualTime(DateUtils.parseDate(handlerCell.getStringCellValue(), "yyyy-MM-dd"));
            }

            importLog.setCol(BALANCE_COLUMN_INDEX);
            handlerCell = row.getCell(BALANCE_COLUMN_INDEX);
            if(handlerCell!=null) {
                handlerCell.setCellType(CellType.NUMERIC);
                importExcelRow.setBalance(new BigDecimal(row.getCell(BALANCE_COLUMN_INDEX).getNumericCellValue()));
            }else{
                importExcelRow.setBalance(new BigDecimal(0f));
            }

            importLog.setCol(INVOICE_COLUMN_INDEX);
            handlerCell = row.getCell(INVOICE_COLUMN_INDEX);
            if(handlerCell!=null){
                handlerCell.setCellType(CellType.STRING);
                importExcelRow.setInvoiceNum(handlerCell.getStringCellValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return importExcelRow;
    }

    private static void genTree(Map<String, ImportCacheNode> currentNode, ImportExcelRow excelRow, String[] param, int index) {
        String key = param[index];
        if (currentNode.containsKey(key)) {
            if (index == HOUSE_LEVEL) {
                //递归到house层
                currentNode.get(key).setImportExcelRow(excelRow);
            } else {
                //向下递归
                index++;
                genTree(currentNode.get(key).getChildren(), excelRow, param, index);
            }
        } else {
            createNodeWithPosterity(currentNode, excelRow, param, index);
        }
    }

    private static void createNodeWithPosterity(Map<String, ImportCacheNode> currentNode, ImportExcelRow excelRow, String[] param, int index) {
        //添加节点，包括其后代元素
        String key = param[index];
        ImportCacheNode node;
        if (currentNode.containsKey(key)) {
            node = currentNode.get(key);
        } else {
            ImportCacheNode father = null;
            Iterator<String> iterator = currentNode.keySet().iterator();
            if(iterator.hasNext()){
                father = currentNode.get(iterator.next()).getFather();
            }
            node = new ImportCacheNode(key, index, father);
            //建立当前层级节点索引
            currentNode.put(key, node);
        }
        if (index != HOUSE_LEVEL) {
            //若果不是house层级，则向下添加子节点
            index++;
            node.addChildren(param[index], new ImportCacheNode(param[index], index, node));
            createNodeWithPosterity(node.getChildren(), excelRow, param, index);
        } else {
            node.setImportExcelRow(excelRow);
        }
    }

}
