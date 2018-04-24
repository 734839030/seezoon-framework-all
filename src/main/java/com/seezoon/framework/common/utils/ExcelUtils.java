package com.seezoon.framework.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;

import com.seezoon.framework.common.context.exception.ServiceException;

/**
 * Excel 工具类
 * 
 * @author hdf 2018年4月23日
 */
public class ExcelUtils {

	/**
	 * 导入07 的
	 * 
	 * @param in
	 * @param clazz
	 * @param columns
	 * @return
	 */
	public static <T> List<T> doParse07(InputStream in, Class<T> clazz, String[] columns) {
		try {
			return doParse(in, clazz, columns, false);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * excel 导入 ,建议导入把模板都定义成string
	 * 
	 * @param in
	 *            文件流
	 * @param rowBean
	 *            行对象
	 * @param column
	 *            列字段
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> List<T> doParse(InputStream in, Class<T> clazz, String[] columns, boolean is03Format)
			throws Exception {
		List<T> result = new ArrayList<>();
		Workbook workbook = null;
		if (is03Format) {
			workbook = new HSSFWorkbook(in);
		} else {
			workbook = new XSSFWorkbook(in);
		}
		// 取得sheet的数量
		int sheetSize = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetSize; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			// 取得sheet的总行数
			int rows = sheet.getPhysicalNumberOfRows();
			int cellNum = 0;
			for (int j = 0; j < rows; j++) {
				T rowData = clazz.newInstance();
				if (j == 0) {
					// 以第0行的表头为准
					cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
					if (cellNum != columns.length) {
						throw new ServiceException("表头长度和实际配置columns长度不一致");
					}
				}

				Row row = sheet.getRow(j);
				for (int k = 0; k < cellNum; k++) {
					if (row == null) {
						continue;
					}
					Cell cell = row.getCell(k);
					Object cellValue = null;
					if (null != cell) {
						switch (cell.getCellTypeEnum()) {
						case STRING:
							cellValue = cell.getStringCellValue();
							break;
						case NUMERIC:
							cellValue = cell.getNumericCellValue();
							// 时间
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								cellValue = cell.getDateCellValue();
							}
							break;
						case BOOLEAN:
							cellValue = cell.getBooleanCellValue();
							break;
						case FORMULA:
							cellValue = cell.getCellFormula();
							break;
						case BLANK:
							break;
						default:
							cellValue = null;
							break;
						}
					}
					Field field = ReflectionUtils.findField(clazz, columns[k]);
					if (null == field) {
						throw new ServiceException(columns[k] + " 配置错误");
					}
					ReflectionUtils.makeAccessible(field);
					ReflectionUtils.setField(field, rowData, cellValue);
				}
				result.add(rowData);
			}
		}
		workbook.close();
		return result;
	}

	/**
	 * 导出
	 * 
	 * @param templatePath
	 *            模板地址，自定义比较好看 classPath下的路径
	 * @param colums
	 *            导出的数据列 反射用
	 * @param clazz
	 * @param data
	 * @param out
	 * @param startRow
	 *            从几行开始写数据
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> void doExport(String templatePath, String[] columns, Class<T> clazz, List<T> data,
			OutputStream out, int startRow) throws IOException, IllegalArgumentException, IllegalAccessException {
		ClassPathResource classPathResource = new ClassPathResource(templatePath);
		InputStream inputStream = classPathResource.getInputStream();
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		int size = data.size() + startRow;
		int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
		for (int i = startRow; i < size; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < cellNum; j++) {
				Cell cell = row.createCell(j);
				Field field = ReflectionUtils.findField(clazz, columns[j]);
				if (null == field) {
					throw new ServiceException(columns[j] + " 配置错误");
				}
				ReflectionUtils.makeAccessible(field);
				Object obj = data.get(i - startRow);
				if (field.getType().getSimpleName().equalsIgnoreCase("double")) {
					cell.setCellValue((Double) field.get(obj));
				} else if (field.getType().getSimpleName().equalsIgnoreCase("date")) {
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
					cell.setCellStyle(style);
					cell.setCellValue((Date) field.get(obj));
				} else {
					cell.setCellValue(field.get(obj) == null ? "" : "" + field.get(obj));
				}
			}
		}
		workbook.write(out);
		workbook.close();
	}
}
