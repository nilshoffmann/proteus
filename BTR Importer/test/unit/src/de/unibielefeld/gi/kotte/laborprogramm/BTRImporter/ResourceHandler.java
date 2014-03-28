package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides resource handling for BTRImporter Readers.
 *
 * @author Konstantin Otte
 */
public class ResourceHandler {

    public static void writeResourceToDisk(String resource, File target) {
        InputStream istream = ResourceHandler.class.getResourceAsStream(resource);
        if (istream == null) {
            throw new IllegalArgumentException("Could not find resource for path " + resource + "! Check path!");
        }
        BufferedInputStream bin = new BufferedInputStream(istream, 2048);

        BufferedOutputStream bout;
        try {
            bout = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buffer = new byte[2048];
            int bytesRead = 0;
            while (true) {
                bytesRead = bin.read(buffer, 0, 2048);
                if (bytesRead == -1) {
                    break;
                }
                bout.write(buffer, 0, bytesRead);
            }
            bout.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResourceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
