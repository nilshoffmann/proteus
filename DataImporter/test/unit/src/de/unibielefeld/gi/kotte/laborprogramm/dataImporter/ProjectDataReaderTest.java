package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

//import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author kotte
 */
public class ProjectDataReaderTest {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(null);
        if(status == JFileChooser.APPROVE_OPTION) {
            File f= jfc.getSelectedFile();
            //File f = new File("gelImages.xml");
            //f.deleteOnExit();
            //BufferedWriter bos;
            //ResourceHandler.writeResourceToDisk("/resources/gelImages.xml", f);
            ProjectDataReader pdr = new ProjectDataReader();
            IProject proj = pdr.parseProject(f);
            System.out.println(proj);
        }
    }
}
