/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.io.File;

/**
 *
 * @author hoffmann
 */
public class FileConverter implements Converter {

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(File.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        File file = (File) value;
        writer.startNode("path");
        writer.setValue(file.getPath());
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
        File file;
        reader.moveDown();
        file = new File(reader.getValue());
        reader.moveUp();
        return file;
    }
}
