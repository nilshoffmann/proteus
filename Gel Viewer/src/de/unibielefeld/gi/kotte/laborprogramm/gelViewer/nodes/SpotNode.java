/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.PropertySupport.ReadOnly;
import org.openide.nodes.PropertySupport.ReadWrite;
import org.openide.nodes.Sheet;

/**
 *
 * @author hoffmann
 */
public class SpotNode extends BeanNode<ISpot> {

    public SpotNode(ISpot bean) throws IntrospectionException {
        super(bean);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();

        System.out.println("creating property sheet for SpotGroup node");
        final ISpot spot = getLookup().lookup(ISpot.class);
        final ISpotGroup sg = spot.getGroup();
        if (sg != null) {
            Sheet.Set set = Sheet.createPropertiesSet();
            Property numberProp = new ReadOnly<Integer>("number", Integer.class,
                    "Spot group number", "The spot number of this group's spots.") {

                @Override
                public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                    return sg.getNumber();
                }
            };
            set.put(numberProp);

            Property labelProp = new ReadWrite<String>("label", String.class,
                    "user defined label", "The user defined label for this spot group.") {

                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return sg.getLabel();
                }

                @Override
                public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    sg.setLabel(val);
                    for (ISpot spot : sg.getSpots()) {
                        spot.setLabel(val);
                    }
                }
            };
            set.put(labelProp);
            sheet.put(set);
        }
        return sheet;
    }
}
