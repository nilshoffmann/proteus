package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes.spotGroups;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
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

/**
 * Node representing groups of gel spots.
 *
 * @author kotte
 */
public class SpotGroupNode extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotGroupIcon.png";

    public SpotGroupNode(ISpotGroup isg, Lookup lkp) {
//        super(Children.create(new SpotGroupChildNodeFactory(new ProxyLookup(lkp,Lookups.fixed(isg))), true), new ProxyLookup(lkp,Lookups.fixed(isg)));
        super(Children.create(new SpotGroupChildNodeFactory(Lookups.fixed(isg, lkp.lookup(IProteomicProject.class), lkp.lookup(IProject.class))), true), Lookups.fixed(isg, lkp.lookup(IProteomicProject.class), lkp.lookup(IProject.class)));
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
                for (ISpot spot : sg.getSpots()) {
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
                for (ISpot spot : sg.getSpots()) {
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
        StringBuilder displayName = new StringBuilder();
        ISpotGroup group = getLookup().lookup(ISpotGroup.class);
        //group number
        displayName.append('#');
        displayName.append(getLookup().lookup(ISpotGroup.class).getNumber());
        displayName.append(' ');
        //group label
        if (group.getLabel() != null && !group.getLabel().isEmpty()) {
            displayName.append("labeled '");
            displayName.append(getLookup().lookup(ISpotGroup.class).getLabel());
            displayName.append("' ");
        }
        //picking and identification status
        switch (SpotGroupStatus.getSpotGroupStatus(group)) {
            case ALL_UNPICKED:
                displayName.append("unpicked");
                break;
            case HAS_IDENT:
                displayName.append("identified");
                break;
            case PARTIALLY_PROCESSED:
                displayName.append("partially processed");
                break;
        }
        
        return displayName.toString();
    }
    
    @Override
    public String getShortDescription() {
        StringBuilder spotGroupLabel = new StringBuilder();
        ISpotGroup group = getLookup().lookup(ISpotGroup.class);
        boolean filled = false;
        for (ISpot spot : group.getSpots()) {
            if (spot.getStatus() == SpotStatus.PICKED) {
                IWell96 well96 = spot.getWell();
                if (well96.getStatus() == Well96Status.PROCESSED) {
                    for (IWell384 well384 : well96.get384Wells()) {
                        for (IIdentificationMethod method : well384.getIdentification().getMethods()) {
                            for (IIdentification ident : method.getIdentifications()) {
                                if (filled) {
                                    spotGroupLabel.append(", ");
                                }
//                                spotGroupLabel.append(ident.getAbbreviation());
                                spotGroupLabel.append(ident.getName());
                                filled = true;
                            }
                        }
                    }
                }
            }
        }
        if (!filled) {
            spotGroupLabel.append("unidentified");
        }

        return spotGroupLabel.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ISpotGroup.PROPERTY_LABEL) || evt.getPropertyName().equals(PROP_DISPLAY_NAME)) {
            fireDisplayNameChange(null, getDisplayName());
        } else if (evt.getPropertyName().equals(PROP_NAME)) {
            fireNameChange(null, getName());
        } else if (evt.getPropertyName().equals(PROP_SHORT_DESCRIPTION)) {
            fireShortDescriptionChange(null, getShortDescription());
        }
        firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}
