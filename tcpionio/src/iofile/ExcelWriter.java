package iofile;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelWriter {

    private List<String> attributeNames;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;

    public void setAttributeNames(List<String> attributeNames) {
        this.attributeNames = attributeNames;
    }

    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void closeWorkbook(XSSFWorkbook workbook) {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            int j = 0;
            for (int i = 1; i <= 10; i++) {
                sheet.getRow(i).getCell(0).setCellValue(Parser.getNoList().get(j));
                sheet.getRow(i).getCell(1).setCellValue(Parser.getTitleList().get(j));
                sheet.getRow(i).getCell(2).setCellValue(Parser.getCategoryList().get(j));
                sheet.getRow(i).getCell(3).setCellValue(Parser.getLangList().get(j));
                sheet.getRow(i).getCell(4).setCellValue(Parser.getAuthorList().get(j));
                sheet.getRow(i).getCell(5).setCellValue(Parser.getYearList().get(j));
                sheet.getRow(i).getCell(6).setCellValue(Parser.getPriceList().get(j));
                if (j <= Parser.getNoList().size()) {
                    j++;
                }
            }
            workbook.write(new FileOutputStream("C:\\test\\upload\\socket_excel.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWorkbook(workbook);
        }
    }

    public void execute() {
        setAttributeNames(List.of("NO", "TITLE", "CATEGORY", "LANG", "AUTHOR", "YEAR", "PRICE"));
        //Create a 엑셀파일
        setWorkbook(new XSSFWorkbook());
        //Create a 엑셀시트
        sheet = workbook.createSheet("sheet1");

        //엑셀 시트에 행 지정
        //XSSFRow row; // 첫 행

        //11행 7열(컬럼명 맨위 1행)
        for (int j = 0; j < 11; j++) {
            row = sheet.createRow(j);
            for (int m = 0; m < attributeNames.size(); m++) {
                row.createCell(m);
            }
        }

        //컬럼명 추가
        for (int i = 0; i < attributeNames.size(); i++)
            sheet.getRow(0).getCell(i).setCellValue(attributeNames.get(i));
    }

    public ExcelWriter() {

    }



}
