package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
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
 * Node representing a group of biological replicates of 2D gels.
 *
 * @author Konstantin Otte
 */
public class BioRepGelGroupNode extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/BioRepGelGroupIcon.png";

    public BioRepGelGroupNode(IBioRepGelGroup ibrgg, Lookup lkp) {
        super(Children.create(new BioRepGelGroupChildNodeFactory(new ProxyLookup(lkp,Lookups.fixed(ibrgg))), true), new ProxyLookup(lkp,Lookups.singleton(ibrgg)));
        ibrgg.addPropertyChangeListener(WeakListeners.propertyChange(this, ibrgg));
    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[7];
        nodeActions[0] = CommonProjectActions.newFileAction();
//        List<? extends Action> actions = Utilities.actionsForPath("/Actions/ProteomikLaborProgramm/"+getClass().getSimpleName()+"/");
        List<? extends Action> actions = Utilities.actionsForPath("/Projects/ProteomikLaborProgramm/");
        List<Action> allActions = new ArrayList<Action>(Arrays.asList(nodeActions));
        allActions.addAll(actions);
        allActions.addAll(Arrays.asList(super.getActions(arg0)));
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final IBioRepGelGroup ibrgg = getLookup().lookup(IBioRepGelGroup.class);
        System.out.println("creating property sheet for BioRepGelGroup node");

        Property nameProp = new ReadWrite<String>("name", String.class,
                "Group name", "The name of this biological replicates gel group.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return ibrgg.getName();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ibrgg.setName(val);
            }
        };
        set.put(nameProp);

        Property descProp = new ReadWrite<String>("description", String.class,
                "Group description", "This gel group's description.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return ibrgg.getDescription();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ibrgg.setDescription(val);
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
        return getLookup().lookup(IBioRepGelGroup.class).getName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            this.fireDisplayNameChange(null, getDisplayName());
        } else {
//			
//            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.
//                    getNewValue());
        }
    }
}