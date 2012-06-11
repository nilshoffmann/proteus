/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import com.db4o.collections.ActivatableCollection;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 *
 * @author hoffmann
 */
public class ActivatableArrayListConverter extends CollectionConverter {

    public ActivatableArrayListConverter(Mapper mapper) {
        super(mapper);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(ActivatableCollection.class);
    }
}
