package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

//import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author kotte
 */
public class ExcelReaderTest {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(null);
        if(status == JFileChooser.APPROVE_OPTION) {
            File f= jfc.getSelectedFile();
            //File f = new File("gelImages.xml");
            //f.deleteOnExit();
            //BufferedWriter bos;
            //ResourceHandler.writeResourceToDisk("/resources/gelImages.xml", f);
            ExcelReader er = new ExcelReader();
            er.parseExport(f);
        }
    }
}
