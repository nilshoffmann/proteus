package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 * Reads spot informations from the Delta2D table output.
 *
 * @author Konstantin Otte
 */
public class ExcelReader {

    Map<Integer, IGel> gelsByColumn = null;
    Map<Integer, SpotDatum> datatypeByColumn = null;
    Map<IGel, Map<Integer, ISpot>> gelSpotsMap = null;
//    boolean dummiesInitialized = false;
//    ITechRepGelGroup trggDummy = null;
//    IBioRepGelGroup brggDummy = null;
//    ILogicalGelGroup lggDummy = null;

    private static enum SpotDatum {

        NORM_VOLUME, GREY_VOLUME, SPOTID;// LABEL, XPOS, YPOS;
    }

    public ExcelReader(IProject project) {
        this.gelsByColumn = new LinkedHashMap<Integer, IGel>();
        this.datatypeByColumn = new LinkedHashMap<Integer, SpotDatum>();
        
        //construct a new data structure to access all spots of all gels by spotID
        this.gelSpotsMap = new LinkedHashMap<IGel, Map<Integer, ISpot>>();
        for (ILogicalGelGroup lgg : project.getGelGroups()) {
            for (IBioRepGelGroup brgg : lgg.getGelGroups()) {
                for (ITechRepGelGroup trgg : brgg.getGelGroups()) {
                    for (IGel gel : trgg.getGels()) {
                        Map<Integer, ISpot> spots = new LinkedHashMap<Integer, ISpot>();
                        for (ISpot spot : gel.getSpots()) {
                            spots.put(spot.getNumber(), spot);
                        }
                        this.gelSpotsMap.put(gel, spots);
                    }
                }
            }
        }
    }

    private IGel getGelByName(String gelname) {
        for (IGel gel : gelSpotsMap.keySet()) {
            assert gelname != null && gel.getName() != null;
            if (gel.getName().equals(gelname)) {
                return gel;
            }
        }
        throw new IllegalArgumentException("Mismatch of gel name defined in project! Gel '" + gelname + "' can't be found. Please check for missing/extraneous gels in Delta2D project and spot export (.xlsx)!");
//        //by now we should have found the gel
//        Logger.getLogger(ExcelReader.class.getName()).log(Level.WARNING, "ExcelReader creates gel ''{0}''.", gelname);
//        //if not, set up a new gel
//        IGel gel = Lookup.getDefault().lookup(IGelFactory.class).createGel();
//        gel.setName(gelname);
//        //add new gel to project using dummy gel groups
//        initDummies();
//        if (!project.getGelGroups().contains(this.lggDummy)) {
//            project.addGelGroup(this.lggDummy);
//        }
//        this.trggDummy.addGel(gel);
//        return gel;
    }

//    private void initDummies() {
//        if (!this.dummiesInitialized) {
//            trggDummy = Lookup.getDefault().lookup(ITechRepGelGroupFactory.class).createTechRepGelGroup();
//            trggDummy.setDescription("Dummy gel group created by the ExcelReader");
//            brggDummy = Lookup.getDefault().lookup(IBioRepGelGroupFactory.class).createBioRepGelGroup();
//            brggDummy.addGelGroup(trggDummy);
//            trggDummy.setParent(brggDummy);
//            brggDummy.setDescription("Dummy gel group created by the ExcelReader");
//            lggDummy = Lookup.getDefault().lookup(ILogicalGelGroupFactory.class).createLogicalGelGroup();
//            lggDummy.addGelGroup(brggDummy);
//            brggDummy.setParent(lggDummy);
//            lggDummy.setDescription("Dummy gel group created by the ExcelReader");
//
//            this.dummiesInitialized = true;
//        }
//    }
    private void parseHeader(Row header) {
        //Pattern definition (re-usable)
        Pattern normVolPattern = Pattern.compile("normalized volume '(.*)'");
        Pattern greyVolPattern = Pattern.compile("integrated grey volume without background '(.*)'");
        Pattern spotIDPattern = Pattern.compile("'(.*)' spot ID given by Delta2D");
//        Pattern labelPattern = Pattern.compile("user defined label '(.*)'");
//        Pattern xPosPattern = Pattern.compile("horizontal position on gel image \\(left = 0\\) of center '(.*)'");
//        Pattern yPosPattern = Pattern.compile("vertical position on gel image \\(top = 0\\) of center '(.*)'");

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
//                Matcher labelMatcher = labelPattern.matcher(line);
//                while (labelMatcher.find()) {
//                    registerColumnInformation(column, labelMatcher.group(1), SpotDatum.LABEL);
//                }
//                Matcher xPosMatcher = xPosPattern.matcher(line);
//                while (xPosMatcher.find()) {
//                    registerColumnInformation(column, xPosMatcher.group(1), SpotDatum.XPOS);
//                }
//                Matcher yPosMatcher = yPosPattern.matcher(line);
//                while (yPosMatcher.find()) {
//                    registerColumnInformation(column, yPosMatcher.group(1), SpotDatum.YPOS);
//                }
            } else {
                Logger.getLogger(ExcelReader.class.getName()).log(Level.WARNING, "Header Zelle {0} ohne String Datentyp", c.getColumnIndex());
            }
        }
    }

    private void registerColumnInformation(int column, String gelname, SpotDatum datum) {
//        System.out.println("Adding column info. idx:" + column + " gel:'" + gelname + "' type:" + datum);
        gelsByColumn.put(column, getGelByName(gelname));
        datatypeByColumn.put(column, datum);
    }

    /**
     * Parses Excel file f and writes spot informations to the spots of the
     * project.
     *
     * @param f Excel file
     * @param project project data objects to fill with informations
     */
    public void parseExport(File f) {
        //open Excel file as workbook
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new FileInputStream(f));
        } catch (IOException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        assert (workbook != null);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> iterR = sheet.iterator();
        //read row 0 as header row
        parseHeader(iterR.next());
        while (iterR.hasNext()) {
            int currentSpotID = 0;
            Map<IGel, Double> normVolumes = new LinkedHashMap<IGel, Double>();
            Map<IGel, Double> greyVolumes = new LinkedHashMap<IGel, Double>();

            //read cells in row
            Iterator<Cell> iterC = iterR.next().iterator();
            while (iterC.hasNext()) {
                Cell cell = iterC.next();
                int columnIndex = cell.getColumnIndex();
                if (datatypeByColumn.containsKey(columnIndex)) {
                    SpotDatum datum = datatypeByColumn.get(columnIndex);
                    switch (datum) {
                        case NORM_VOLUME:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                normVolumes.put(gelsByColumn.get(columnIndex), cell.getNumericCellValue());
                            }
                            break;
                        case GREY_VOLUME:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                greyVolumes.put(gelsByColumn.get(columnIndex), cell.getNumericCellValue());
                            }
                            break;
                        case SPOTID:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                currentSpotID = (int) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                currentSpotID = Integer.parseInt(cell.getStringCellValue());
                            } else {
                                throw new IllegalStateException("Cell " + cell.getColumnIndex() + ":" + cell.getRowIndex()
                                        + " doesn't contain a numerical value");
                            }
                            break;
//                        case LABEL:
//                            break;
//                        case XPOS:
//                            break;
//                        case YPOS:
//                            break;
                        default:
                            break;
                    }
                }
            } //done reading cells of the current row
            ISpot spot;//note: all spots in one row belong to the same group,
                       //      thus sharing the same Delta2D spotID
            //set normalized volumes for the spots on all gels
            for (IGel gel : normVolumes.keySet()) {
                spot = gelSpotsMap.get(gel).get(currentSpotID);
                if (spot != null) {
                    spot.setNormVolume(normVolumes.get(gel));
                }
            }
            //set integrated grey volumes for the spots on all gels
            for (IGel gel : greyVolumes.keySet()) {
                spot = gelSpotsMap.get(gel).get(currentSpotID);
                if (spot != null) {
                    spot.setNormVolume(greyVolumes.get(gel));
                }
            }
        }
    }

//    private int cellToInt(Cell cell) {
//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_NUMERIC:
//                return (int) cell.getNumericCellValue();
//            case Cell.CELL_TYPE_BLANK:
//                return -1;
//            case Cell.CELL_TYPE_STRING:
//                String str = cell.getStringCellValue();
//                return Integer.parseInt(str);
//        }
//        throw new IllegalStateException("Cell " + cell.getColumnIndex() + ":" + cell.getRowIndex() + " doesn't contain a numerical value");
//    }
//    public static String typeToString(Cell cell) {
//        int cellType = cell.getCellType();
//        switch(cellType) {
//            case Cell.CELL_TYPE_BLANK:
//                return "blank";
//            case Cell.CELL_TYPE_BOOLEAN:
//                return "boolean";
//            case Cell.CELL_TYPE_ERROR:
//                return "error";
//            case Cell.CELL_TYPE_FORMULA:
//                return "formula";
//            case Cell.CELL_TYPE_NUMERIC:
//                return "numeric";
//            case Cell.CELL_TYPE_STRING:
//                return "string";
//        }
//        return "unknown";
//    }
}
