package com.madiot.enterprise.common.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {

	private static String WRONG_MODEL = "EXCEL模板不正确";
	private static String DATE_FORMAT_ERROR = "日期格式不正确!";

	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle() {
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(row.getCell(i));
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List readExcelContent(Class clz) throws IllegalAccessException,
			InstantiationException {
		List result = new ArrayList();
		Map<Integer, String> content = new HashMap<Integer, String>();
		String str = "";
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			ExcelModel model = (ExcelModel) clz.newInstance();
			int j = 0;
			while (j < colNum) {
				HSSFCell cell = row.getCell(j);
				if (cell != null) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					str = cell.getStringCellValue().trim();
					content.put(j, str);
				}
				j++;
			}
			model.setValues(content);
			content = new HashMap<Integer, String>();
			result.add(model);
		}
		return result;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(HSSFCell cell) throws Exception {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			throw new Exception(DATE_FORMAT_ERROR);
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	/**
	 * 验证模板是否正确
	 * 
	 * @param titles
	 * @return
	 */
	public boolean validateTitle(String[] titles, String[] excelTitle) {
		if (excelTitle.length != titles.length) {
			return false;
		}
		for (int i = 0; i < excelTitle.length; i++) {
			if (!excelTitle[i].equals(titles[i].trim())) {
				return false;
			}
		}
		return true;
	}

	public List excelToModel(Class clz, InputStream inputStream)
			throws Exception {
		fs = new POIFSFileSystem(inputStream);
		wb = new HSSFWorkbook(fs);

		String[] title = readExcelTitle();
		ExcelModel model = (ExcelModel) clz.newInstance();
		if (!this.validateTitle(title, model.getTitles())) {
			throw new Exception(WRONG_MODEL);
		}
		return readExcelContent(clz);
	}

	/*
	 * public static void main(String[] args) { try { // 对读取Excel表格标题测试
	 * InputStream is = new FileInputStream("f:\\新生.xls"); ExcelReader
	 * excelReader = new ExcelReader(); List<StudentExcelModel> modelList =
	 * excelReader.excelToModel(StudentExcelModel.class, is);
	 * System.out.println(modelList.size()); for(StudentExcelModel model :
	 * modelList){ System.out.println(model.getStudentName()); } } catch
	 * (FileNotFoundException e) { System.out.println("未找到指定路径的文件!");
	 * e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); } }
	 */
}
