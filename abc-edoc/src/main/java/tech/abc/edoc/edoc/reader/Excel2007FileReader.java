package tech.abc.edoc.edoc.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * excel2007及以上版本文件读取器（后缀名xlsx)
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class Excel2007FileReader implements FileReader {
    @Override
    public String readFile(String filePath) {


        XSSFWorkbook xssfWorkbook = null;
        InputStream fis = null;
        StringBuffer sb=new StringBuffer();
        try {
            fis = new FileInputStream(filePath);
            xssfWorkbook = new XSSFWorkbook(fis);
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xRow = xSheet.getRow(rowNum);
                    if (xRow == null) {
                        continue;
                    }
                    // 循环列Cell
                    for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
                        XSSFCell xCell = xRow.getCell(cellNum);
                        if (xCell == null) {
                            continue;
                        }
                        CellType cellTypeEnum = xCell.getCellTypeEnum();
                        if ( cellTypeEnum==CellType.BOOLEAN) {
                            sb.append(xCell.getBooleanCellValue());
                        } else if (cellTypeEnum == CellType.NUMERIC) {
                            sb.append(xCell.getNumericCellValue());
                        } else {
                            sb.append(xCell.getStringCellValue());
                        }
                    }
                }
            }
            return sb.toString();

        } catch (IOException e) {
            throw new CustomException(FileException.READ_FAILURE, e.getMessage());
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != xssfWorkbook) {
                    xssfWorkbook.close();
                }
            } catch (IOException e) {
                log.error("关闭文件资源失败", e);
            }
        }
    }
}