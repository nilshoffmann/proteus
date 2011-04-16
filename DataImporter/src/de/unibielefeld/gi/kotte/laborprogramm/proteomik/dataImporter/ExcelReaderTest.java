package de.unibielefeld.gi.kotte.laborprogramm.proteomik.dataImporter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.junit.Test;

/**
 *
 * @author kotte
 */
public class ExcelReaderTest {

    public static void main(String[] args) {
        new ExcelReaderTest().testStuff();
    }

    //@Test
    public void testStuff() {
        try {
            Workbook workbook = null;
            File f = new File("Export_1.xlsx");
            //f.deleteOnExit();
            BufferedOutputStream bos;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(f));
                BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream("/de/unibielefeld/gi/kotte/laborprogramm/proteomik/dataImporter/resources/Export_1.xlsx"));
                byte[] buffer = new byte[2048];
                try {
                    while (bis.read(buffer) != -1) {
                        bos.write(buffer);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                bos.flush();
                bos.close();

                try {
                    workbook = WorkbookFactory.create(bis);
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
                }

                assert (workbook != null);

                System.out.printf("Succesfully read workbook with %i sheets", workbook.getNumberOfSheets());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
