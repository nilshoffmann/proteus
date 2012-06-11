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
import java.awt.geom.GeneralPath;
import java.io.File;

/**
 *
 * @author hoffmann
 */
public class GeneralPathConverter implements Converter {

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(GeneralPath.class);
    }

    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        GeneralPath gp = (GeneralPath) value;
        writer.startNode("generalPath");
        GeneralPathPersistenceUnit gppu = new GeneralPathPersistenceUnit();
        writer.setValue(gppu.encode(gp));
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
        GeneralPath gp;
        reader.moveDown();
        GeneralPathPersistenceUnit gppu = new GeneralPathPersistenceUnit();
        gp = gppu.decode(reader.getValue());
        reader.moveUp();
        return gp;
    }
    
}
