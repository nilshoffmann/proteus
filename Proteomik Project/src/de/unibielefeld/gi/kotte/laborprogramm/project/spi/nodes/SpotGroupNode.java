package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport.ReadWrite;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 * Node representing groups of gel spots.
 *
 * @author kotte
 */
public class SpotGroupNode extends AbstractNode {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotGroupIcon.png";

    public SpotGroupNode(ISpotGroup isg, Lookup lkp) {
        super(Children.LEAF,Lookups.fixed(isg));
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        System.out.println("creating property sheet for SpotGroup node");
        final ISpotGroup sg = getLookup().lookup(ISpotGroup.class);

        Property numberProp = new ReadWrite<Integer>("number", Integer.class,
                "Spot group number", "The spot number of this group's spots.") {

            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                return sg.getNumber();
            }

            @Override
            public void setValue(Integer val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                sg.setNumber(val);
                for(ISpot spot: sg.getSpots()) {
                    spot.setNumber(val);
                }
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
                for(ISpot spot: sg.getSpots()) {
                    spot.setLabel(val);
                }
            }
        };
        set.put(labelProp);

        sheet.put(set);
        return sheet;
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(ICON_PATH);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        StringBuilder spotGroupLabel = new StringBuilder();
        spotGroupLabel.append("Spot group #");
        spotGroupLabel.append(getLookup().lookup(ISpotGroup.class).getNumber());
        if(!getLookup().lookup(ISpotGroup.class).getLabel().isEmpty()){
            spotGroupLabel.append("'");
            spotGroupLabel.append(getLookup().lookup(ISpotGroup.class).getLabel());
            spotGroupLabel.append("'");
        }
        return spotGroupLabel.toString();
    }
}
