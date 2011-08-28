package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
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
 * Node representing a 2D gel.
 *
 * @author kotte
 */
public class GelNode extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/GelIcon.png";

    public GelNode(IGel gel, Lookup lkp) {
        super(Children.LEAF, new ProxyLookup(lkp, Lookups.fixed(gel, Lookup.
                getDefault().lookup(IGelOpenCookie.class))));
        gel.addPropertyChangeListener(WeakListeners.propertyChange(this, gel));
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
        return getLookup().lookup(IGel.class).getName();
    }

    @Override
    public String getShortDescription() {
        return getLookup().lookup(IGel.class).getDescription();
    }

    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final IGel gel = getLookup().lookup(IGel.class);
        System.out.println("creating property sheet for gel node");
        Property filenameProp = new ReadWrite<String>("filename", String.class,
                "Gel location", "The location of this gel's image file.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return gel.getFilename();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                gel.setFilename(val);
            }
        };
        Property nameProp = new ReadWrite<String>("name", String.class,
                "Gel name", "The gel's name") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return gel.getName();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                gel.setName(val);
            }
        };
        Property descriptionProp = new ReadWrite<String>("description",
                String.class, "Gel description", "A description of this gel") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return gel.getDescription();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                gel.setDescription(val);
            }
        };

        set.put(filenameProp);
        set.put(nameProp);
        set.put(descriptionProp);

        sheet.put(set);
        return sheet;

    }

    @Override
    public Action[] getActions(boolean arg0) {
        List<? extends Action> actions = Utilities.actionsForPath(
                "/Actions/GelNode");
        List<Action> allActions = new LinkedList<Action>(actions);
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            this.fireDisplayNameChange(null, getDisplayName());
        } else {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.
                    getNewValue());
        }
    }
}
