package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
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
 * Node representing a 96 well microplate.
 *
 * @author kotte
 */
public class Plate96Node extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/Plate96Icon.png";

    public Plate96Node(IPlate96 plate, Lookup lkp) {
        super(Children.LEAF,new ProxyLookup(lkp,Lookups.fixed(plate,Lookup.getDefault().lookup(IPlate96OpenCookie.class))));
        plate.addPropertyChangeListener(WeakListeners.propertyChange(this, plate));
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
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final IPlate96 obj = getLookup().lookup(IPlate96.class);
        System.out.println("creating property sheet for Plate96 node");
        Property nameProp = new ReadWrite<String>("name", String.class,
                "Plate96 name", "The 96 well plate's name") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return obj.getName();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                obj.setName(val);
            }
        };
        Property descriptionProp = new ReadWrite<String>("description",
                String.class, "Plate96 description", "A description of this 96 well plate") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return obj.getDescription();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                obj.setDescription(val);
            }
        };

        set.put(nameProp);
        set.put(descriptionProp);

        sheet.put(set);
        return sheet;

    }

    @Override
    public String getDisplayName() {
        return getLookup().lookup(IPlate96.class).getName();
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/Plate96Node");
//        System.out.println("Retrieved actions for path :"+"/Actions/GelNode/:"+actions);
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
        if (evt.getPropertyName().equals("name")) {
            this.fireDisplayNameChange(null, getDisplayName());
        } else {
//            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.
//                    getNewValue());
        }
    }
}
