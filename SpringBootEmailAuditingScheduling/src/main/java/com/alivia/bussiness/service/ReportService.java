package com.alivia.bussiness.service;



import com.alivia.bussiness.dto.Order;
import com.alivia.bussiness.repositary.OrderRepositary;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
  @Autowired
  private OrderRepositary repo;
  
  public byte[] generateReport() throws IOException {
    List<Order> data = this.repo.findAll();
    SXSSFWorkbook workbook = new SXSSFWorkbook();
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      try {
        SXSSFSheet sheet = workbook.createSheet("Orders");
        writeHeaderLine(sheet);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont)workbook.createFont();
        style.setFont((Font)font);
        int rowCount = 1;
        for (Order order : data) {
          SXSSFRow sXSSFRow = sheet.createRow(rowCount++);
          int columnCount = 0;
          createCell((Row)sXSSFRow, columnCount++, order.getId(), style);
          createCell((Row)sXSSFRow, columnCount++, order.getName(), style);
          createCell((Row)sXSSFRow, columnCount++, Integer.valueOf(order.getQuantity()), style);
          createCell((Row)sXSSFRow, columnCount++, Double.valueOf(order.getPrice()), style);
        } 
        workbook.write(outputStream);
        byte[] arrayOfByte = outputStream.toByteArray();
        outputStream.close();
        workbook.close();
        return arrayOfByte;
      } catch (Throwable throwable) {
        try {
          outputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Throwable throwable) {
      try {
        workbook.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  private void writeHeaderLine(SXSSFSheet sheet) {
    SXSSFRow sXSSFRow = sheet.createRow(0);
    createHeaderCell((Row)sXSSFRow, 0, "ORDER_ID");
    createHeaderCell((Row)sXSSFRow, 1, "ORDER_NAME");
    createHeaderCell((Row)sXSSFRow, 2, "ORDER_QTY");
    createHeaderCell((Row)sXSSFRow, 3, "ORDER_PRICE");
  }
  
  private void createHeaderCell(Row row, int columnCount, String value) {
    Cell cell = row.createCell(columnCount);
    cell.setCellValue(value);
  }
  
  private void createCell(Row row, int columnCount, Object value, CellStyle style) {
    Cell cell = row.createCell(columnCount);
    if (value instanceof Integer) {
      cell.setCellValue(((Integer)value).intValue());
    } else if (value instanceof String) {
      cell.setCellValue((String)value);
    } else if (value instanceof Double) {
      cell.setCellValue(((Double)value).doubleValue());
    } 
    cell.setCellStyle(style);
  }
}
