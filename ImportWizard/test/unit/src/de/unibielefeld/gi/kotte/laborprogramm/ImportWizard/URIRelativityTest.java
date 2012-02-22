/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.ImportWizard;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.util.Exceptions;

/**
 *
 * @author nilshoffmann
 */
public class URIRelativityTest {

    public URIRelativityTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void relativeURI() {
        String prefix = "/Users/test";
        String suffix = "text/test/blablabl.txt";
        
        File baseDirectory = new File(prefix);
        File relativeFile = new File(suffix);
        //resolved file
        File resolvedFile = new File(prefix, suffix);
        URI resolvedFileUri = resolvedFile.toURI();
        URI controlUri = new File(baseDirectory, relativeFile.getPath()).toURI();
        System.out.println(
                "Resolved: " + resolvedFileUri + "; Control: " + controlUri);
        assertEquals(controlUri, resolvedFileUri);

        URI baseDirectoryUri = baseDirectory.toURI();
        URI relativeFileUri;
        try {
            relativeFileUri = new URI(relativeFile.getPath());
            System.out.println("baseDirectoryUri.opaque: " + baseDirectoryUri.
                    isOpaque());
            System.out.println("baseDirectoryUri.absolute: " + baseDirectoryUri.
                    isAbsolute());
            System.out.println("relativeFileUri.opaque: " + relativeFileUri.
                    isOpaque());
            System.out.println("relativeFileUri.absolute: " + relativeFileUri.
                    isAbsolute());
            File baseDirectory2 = new File(baseDirectoryUri);
            File resolvedFile2 = new File(baseDirectory2,relativeFileUri.getPath());
            assertEquals(controlUri.normalize(), resolvedFile2.toURI());
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }


//        URI resolvedFileUri = resolvedFile.toURI();
//
//        //relative file
//        URI relativeFileUri = relativeFile.toURI();
//        URI baseDirectoryUri = baseDirectory.toURI();
//
//        System.out.println("relativeFile: " + relativeFile.getPath());
//        System.out.println("baseDirectory: " + baseDirectory.getPath());
//        System.out.println("relativeFileUri: " + relativeFileUri);
//        System.out.println("baseDirectoryUri: " + baseDirectoryUri);
//
//        URI resolvedUri = baseDirectoryUri.resolve(relativeFileUri).normalize();
//        System.out.println("resolvedUri: " + resolvedUri);
//        System.out.println("resolvedFileUri: " + resolvedFileUri);
//
//        URI relativizedUri = baseDirectoryUri.relativize(resolvedFileUri);
//        System.out.println("relativizedUri: " + relativizedUri);
//        assertEquals(relativizedUri, relativeFileUri);
////            assertEquals(relativizedUri,)
////            assertEquals(resolvedFile.toURI(), resolvedUri);
    }
}
