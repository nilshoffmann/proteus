package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProject;
import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * Node representing a Pathway Project.
 *
 * @author Konstantin Otte
 */
public class PathwayProjectNode extends BeanNode<IPathwayUIProject> implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/pathway/project/resources/PathwayProjectIcon.png";
    
    public PathwayProjectNode(IPathwayUIProject bean, Children children, Lookup lkp) throws IntrospectionException {
        super(bean, children, new ProxyLookup(lkp,Lookups.singleton(bean)));
        bean.addPropertyChangeListener(WeakListeners.propertyChange(this, bean));
    }

    public PathwayProjectNode(IPathwayUIProject bean, Children children) throws IntrospectionException {
        super(bean, children, Lookups.singleton(bean));
        bean.addPropertyChangeListener(WeakListeners.propertyChange(this, bean));
    }

    public PathwayProjectNode(IPathwayUIProject bean) throws IntrospectionException {
        super(bean,Children.LEAF,
                Lookups.singleton(bean));
        setName(bean.getProjectDirectory().getName());
//        setShortDescription(getName());
        bean.addPropertyChangeListener(WeakListeners.propertyChange(this, bean));
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
        final IPathwayProject proj = getLookup().lookup(IPathwayProject.class);
        System.out.println("creating property sheet for Project node");

        Property nameProp = new PropertySupport.ReadWrite<String>("name", String.class,
                "Project name", "The project name.") {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return proj.getName();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                proj.setName(val);
            }
        };
        set.put(nameProp);
        sheet.put(set);
        return sheet;
    }

//    @Override
//    public String getDisplayName() {
//        if (project != null) {
//            return project.getName();
//        }
//        return "<NA>";
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Received change event on ProjectNode: " + evt);
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] nodeActions = new Action[2];
        nodeActions[0] = CommonProjectActions.deleteProjectAction();
        nodeActions[1] = CommonProjectActions.closeProjectAction();
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/PathwayProjectNode");
        List<Action> allActions = new LinkedList<Action>(actions);
        allActions.addAll(Arrays.asList(super.getActions(context)));
        allActions.add(null);
        allActions.addAll(Arrays.asList(nodeActions));
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        Action[] actions = getActions(false);
        if (actions.length > 0) {
            return actions[0];
        }
        return null;
    }
}
