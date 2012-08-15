package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport.ReadWrite;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * Node representing groups of gel spots.
 *
 * @author kotte
 */
public class SpotGroupNode extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotGroupIcon.png";

    public SpotGroupNode(ISpotGroup isg, Lookup lkp) {
//        super(Children.create(new SpotGroupChildNodeFactory(new ProxyLookup(lkp,Lookups.fixed(isg))), true), new ProxyLookup(lkp,Lookups.fixed(isg)));
        super(Children.create(new SpotGroupChildNodeFactory(Lookups.fixed(isg,lkp.lookup(IProteomicProject.class),lkp.lookup(IProject.class))), true), Lookups.fixed(isg,lkp.lookup(IProteomicProject.class),lkp.lookup(IProject.class)));
        isg.addPropertyChangeListener(WeakListeners.propertyChange(this, isg));
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
    public Action[] getActions(boolean arg0) {
        List<? extends Action> actions = Utilities.actionsForPath(
                "/Actions/SpotGroupNode");
        List<Action> allActions = new LinkedList<Action>(actions);
        return allActions.toArray(new Action[allActions.size()]);
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
        if(getLookup().lookup(ISpotGroup.class).getLabel()!=null && !getLookup().lookup(ISpotGroup.class).getLabel().isEmpty()){
            //spotGroupLabel.append("'");
            spotGroupLabel.append(getLookup().lookup(ISpotGroup.class).getLabel());
            //spotGroupLabel.append("'");
        }else{
            spotGroupLabel.append("#");
            spotGroupLabel.append(getLookup().lookup(ISpotGroup.class).getNumber());
        }
        return spotGroupLabel.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ISpotGroup.PROPERTY_LABEL) ||evt.getPropertyName().equals(PROP_DISPLAY_NAME)) {
            fireDisplayNameChange(null, getDisplayName());
        }else if(evt.getPropertyName().equals(PROP_NAME)) {
            fireNameChange(null, getName());
        }else if(evt.getPropertyName().equals(PROP_SHORT_DESCRIPTION)) {
            fireShortDescriptionChange(null, getShortDescription());
        }
        firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}
