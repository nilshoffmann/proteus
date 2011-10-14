/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.wellIdentificationViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 *
 * @author nilshoffmann
 */
public class WellIdentificationNode extends BeanNode {

    public WellIdentificationNode(Object bean, Children children, Lookup lkp) throws IntrospectionException {
        super(bean,children,lkp);
    }

    public WellIdentificationNode(Object bean, Children children) throws IntrospectionException {
        super(bean,children);
    }

    public WellIdentificationNode(Object bean) throws IntrospectionException {
        super(bean);
    }


    @Override
    public PropertySet[] getPropertySets() {
        final IWellIdentification iwi = getLookup().lookup(
                IWellIdentification.class);
        PropertySet ps1 = new PropertySet("wellProperties",
                "Maldi Target Well Properties",
                "Properties of the current well on a 384 well Maldi target plate.") {

            @Override
            public Property<?>[] getProperties() {
                final IWell384 well = iwi.getWell();
                Property<String> wellName = new Property<String>(String.class) {

                    @Override
                    public boolean canRead() {
                        return true;
                    }

                    @Override
                    public String getValue() throws IllegalAccessException, InvocationTargetException {
                        return well.toString();
                    }

                    @Override
                    public boolean canWrite() {
                        return false;
                    }

                    @Override
                    public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    }
                };


                Property<?>[] props = new Property<?>[]{wellName};
                return props;
            }
        };

        PropertySet ps2 = new PropertySet("identificationProperties",
                "Maldi Target Well Identifications",
                "MS1/MS2 Identifications for Maldi target.") {

            @Override
            public Property<?>[] getProperties() {
                final List<IIdentification> idents = iwi.getIdentifications();
                List<Property<?>> propList = new LinkedList<Property<?>>();
                for (final IIdentification iid : idents) {
                    Property<String> idString = new Property<String>(
                            String.class) {

                        @Override
                        public boolean canRead() {
                            return true;
                        }

                        @Override
                        public String getValue() throws IllegalAccessException, InvocationTargetException {
                            return iid.toString();
                        }

                        @Override
                        public boolean canWrite() {
                            return false;
                        }

                        @Override
                        public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        }
                    };
                    propList.add(idString);
                }
                return propList.toArray(new Property[propList.size()]);
            }
        };
        return new PropertySet[]{ps1,ps2};
    }
}
