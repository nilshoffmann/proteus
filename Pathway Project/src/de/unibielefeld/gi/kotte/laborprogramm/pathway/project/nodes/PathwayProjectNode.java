package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProject;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;

/**
 * Node representing a Pathway Project.
 *
 * @author kotte
 */
public class PathwayProjectNode extends AbstractNode implements PropertyChangeListener  {
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/pathway/project/resources/PathwayProjectIcon.png";
    private IPathwayProject project;

    public PathwayProjectNode(IPathwayProject project, Lookup lookup) {
        super(Children.LEAF, lookup);
        this.project = project;
        project.addPropertyChangeListener(WeakListeners.propertyChange(this, project));
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
    
    @Override
    public String getDisplayName() {
        if (project != null) {
            return project.getName();
        }
        return "<NA>";
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Received change event on ProjectNode: " + evt);
    }
    
    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/PathwayProjectNode");
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
}
