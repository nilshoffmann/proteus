package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroupFactory;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openide.util.Lookup;

/**
 * Reads spot informations from the Delta2D table output.
 *
 * @author kotte
 */
public class ExcelReader {

    List<String> gelnames;
    List<SpotDatum> data;

    private static enum SpotDatum {

        NORM_VOLUME, GREY_VOLUME, SPOTID, LABEL, XPOS, YPOS;
    }

    public ExcelReader() {
        gelnames = new ArrayList<String>();
        data = new ArrayList<SpotDatum>();
    }

    private void parseHeader(Row header) {
        //Pattern definition (re-usable)
        Pattern normVolPattern = Pattern.compile("normalized volume '(.*)'");
        Pattern greyVolPattern = Pattern.compile("integrated grey volume without background '(.*)'");
        Pattern spotIDPattern = Pattern.compile("'(.*)' spot ID given by Delta2D");
        Pattern labelPattern = Pattern.compile("user defined label '(.*)'");
        //TODO potentiell noch Koordinatenursprung auslesen
        Pattern xPosPattern = Pattern.compile("horizontal position on gel image \\(left = 0\\) of center '(.*)'");
        Pattern yPosPattern = Pattern.compile("vertical position on gel image \\(top = 0\\) of center '(.*)'");

        Iterator<Cell> iter = header.cellIterator();
        while (iter.hasNext()) {
            Cell c = iter.next();
            if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                String line = c.getStringCellValue();
                int column = c.getColumnIndex();

                Matcher normVolMatcher = normVolPattern.matcher(line);
                while (normVolMatcher.find()) {
                    registerColumnInformation(column, normVolMatcher.group(1), SpotDatum.NORM_VOLUME);
                }
                Matcher greyVolMatcher = greyVolPattern.matcher(line);
                while (greyVolMatcher.find()) {
                    registerColumnInformation(column, greyVolMatcher.group(1), SpotDatum.GREY_VOLUME);
                }
                Matcher spotIDMatcher = spotIDPattern.matcher(line);
                while (spotIDMatcher.find()) {
                    registerColumnInformation(column, spotIDMatcher.group(1), SpotDatum.SPOTID);
                }
                Matcher labelMatcher = labelPattern.matcher(line);
                while (labelMatcher.find()) {
                    registerColumnInformation(column, labelMatcher.group(1), SpotDatum.LABEL);
                }
                Matcher xPosMatcher = xPosPattern.matcher(line);
                while (xPosMatcher.find()) {
                    registerColumnInformation(column, xPosMatcher.group(1), SpotDatum.XPOS);
                }
                Matcher yPosMatcher = yPosPattern.matcher(line);
                while (yPosMatcher.find()) {
                    registerColumnInformation(column, yPosMatcher.group(1), SpotDatum.YPOS);
                }
            } else {
                System.out.println("Header Zelle " + c.getColumnIndex() + " ohne String Datentyp");
            }
        }
    }

    private void registerColumnInformation(int column, String gelname, SpotDatum datum) {
        gelnames.add(column, gelname);
        data.add(column, datum);
    }

    private IGel getGelByName(String gelname) {
        //TODO richtiges Gel aus der Datenbank holen
        return (Lookup.getDefault().lookup(IGelFactory.class)).createGel();
    }

    public void testStuff() {
        Workbook workbook = null;
        try {

            File f = new File("Export_1.xlsx");
            //f.deleteOnExit();
            //BufferedWriter bos;
            try {
                ResourceHandler.writeResourceToDisk("/resources/Export_1.xlsx", f);

                try {
                    workbook = WorkbookFactory.create(new FileInputStream(f));
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        assert (workbook != null);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterR = sheet.iterator();
        parseHeader(iterR.next()); //read row 0 as header row
        while (iterR.hasNext()) { //read rows
            //create the spot group for the current row
            ISpotGroup group = (Lookup.getDefault().lookup(ISpotGroupFactory.class)).createSpotGroup();
            Map<String, ISpot> spotMap = new HashMap<String, ISpot>();
            Iterator<Cell> iterC = iterR.next().iterator();
            while (iterC.hasNext()) { //read cells in row
                Cell cell = iterC.next();
//                System.out.println("Zelle " + cell.getColumnIndex() + "/" + cell.getRowIndex());
                SpotDatum datum = data.get(cell.getColumnIndex());

                //get spot from hash or create new one if it's not there
                ISpot spot = spotMap.get(gelnames.get(cell.getColumnIndex()));
                if (spot == null) {
                    //create spot
                    spot = (Lookup.getDefault().lookup(ISpotFactory.class)).createSpot();
                    spotMap.put(gelnames.get(cell.getColumnIndex()), spot);
                    //add spot to group
                    spot.setGroup(group);
                    group.addSpot(spot);
                    //add spot to gel
                    IGel gel = getGelByName(gelnames.get(cell.getColumnIndex()));
                    gel.addSpot(spot);
                    spot.setGel(gel);
                }
                assert (spot != null);

                switch (datum) {
                    case NORM_VOLUME: //don't read volumes
                        break;
                    case GREY_VOLUME: //don't read volumes
                        break;
                    case SPOTID:
                        assert (cell.getCellType() == Cell.CELL_TYPE_NUMERIC);
                        spot.setNumber((int) cell.getNumericCellValue());
                        break;
                    case LABEL:
                        assert (cell.getCellType() == Cell.CELL_TYPE_STRING);
                        spot.setLabel(cell.getStringCellValue());
                        break;
                    case XPOS:
                        assert (cell.getCellType() == Cell.CELL_TYPE_NUMERIC);
                        spot.setPosX(cell.getNumericCellValue());
                        break;
                    case YPOS:
                        assert (cell.getCellType() == Cell.CELL_TYPE_NUMERIC);
                        spot.setPosY(cell.getNumericCellValue());
                        break;
                    default:

                        break;
                }
            }
            //set spot group number to spot ID of first member
            group.setNumber(group.getSpots().iterator().next().getNumber());
            System.out.println(group);
        }
    }
}
