package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate384OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
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
 * Node representing a 384 well microplate.
 *
 * @author kotte
 */
public class Plate384Node extends AbstractNode implements PropertyChangeListener  {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/Plate384Icon.png";

    public Plate384Node(IPlate384 plate, Lookup lkp) {
        super(Children.LEAF, new ProxyLookup(lkp, Lookups.fixed(plate, Lookup.getDefault().lookup(IPlate384OpenCookie.class))));
        plate.addPropertyChangeListener(WeakListeners.propertyChange(this, plate));
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final IPlate384 plate = getLookup().lookup(IPlate384.class);
        System.out.println("creating property sheet for Plate384 node");

        Property nameProp = new ReadWrite<String>("name", String.class,
                "Plate name", "The name of this 384 well plate.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return plate.getName();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                plate.setName(val);
            }
        };
        set.put(nameProp);

        Property descProp = new ReadWrite<String>("description", String.class,
                "Plate384 description", "This 384 well plate's description.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return plate.getDescription();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                plate.setDescription(val);
            }
        };
        set.put(descProp);

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
        return getLookup().lookup(IPlate384.class).getName();
    }
    
    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/Plate384Node");
        List<Action> allActions = new LinkedList<Action>(actions);
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        Action[] actions = getActions(false);
        if(actions.length>0) {
            return actions[0];
        }
        return null;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(IPlate384.PROPERTY_NAME)) {
            this.fireDisplayNameChange(null, getDisplayName());
        } else {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.
                    getNewValue());
        }
    }
}
