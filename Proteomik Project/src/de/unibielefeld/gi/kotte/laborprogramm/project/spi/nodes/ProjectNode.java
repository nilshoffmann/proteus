package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport.ReadWrite;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.InstanceContent;

/**
 * Node representing a Proteomik Project.
 *
 * @author kotte
 */
public class ProjectNode extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/ProjectIcon.png";
//    private Result<SaveCookie> result = null;
    private InstanceContent content = null;
    public ProjectNode(IProteomicProject ipp) {
        super(Children.create(new ProjectChildNodeFactory(ipp), true), ipp.getLookup());
//        this(ipp, new InstanceContent(), ipp.getLookup());
        //ipp.addPropertyChangeListener(this);
    }

    /**
     * A private constructor that takes an InstanceContent and
     * uses it as internals for the Node lookup and also allow us
     * to modify the content, for example by adding a reference
     * to the node itself or any other object we want to represent.
     *
     * @param ipp the IProteomicProject instance
     * @param content the content created by the first constructor
     * @param lookup
     */
//    private ProjectNode(IProteomicProject ipp, InstanceContent content,
//            Lookup lkp) {
//        super(Children.create(new ProjectChildNodeFactory(ipp), true), lkp);
//        //this.content = content;
//        // adds the node to our own lookup
//        //this.content.add(this);
//        //this.content.add(ipp);
//        ipp.addPropertyChangeListener(this);
//        //ipp.getLookup().lookup(IProject.class).addPropertyChangeListener(this);
//    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[6];
//        CreatePlate96Action cP96Action = new CreatePlate96Action(getLookup().lookup(IProteomicProject.class).getLookup().lookup(IProject.class));
//        cP96Action.addPropertyChangeListener(this);
//        nodeActions[0] = cP96Action;
//        CreatePlate384Action cP384Action = new CreatePlate384Action(getLookup().lookup(IProteomicProject.class).getLookup().lookup(IProject.class));
//        cP384Action.addPropertyChangeListener(this);
//        nodeActions[1] = cP384Action;
        //nodeActions[0] = CommonProjectActions.newFileAction();
        //nodeActions[1] = CommonProjectActions.copyProjectAction();
        final ProjectNode node = this;
        nodeActions[0] = new AbstractAction("Refresh") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                getLookup().lookup(IProteomicProject.class).
                        propertyChange(new PropertyChangeEvent(node,
                        "REFRESH", null, node));
            }
        };
        nodeActions[2] = CommonProjectActions.deleteProjectAction();
        nodeActions[4] = CommonProjectActions.setAsMainProjectAction();

        nodeActions[5] = CommonProjectActions.closeProjectAction();
        List<? extends Action> projectActions = Utilities.actionsForPath(
                "/Actions/ProteomicProject/");
        List<? extends Action> actions = Utilities.actionsForPath(
                "/Projects/ProteomikLaborProgramm/");
        List<Action> allActions = new ArrayList<Action>();
        allActions.addAll(projectActions);
        allActions.addAll(actions);
        allActions.add(null);
        allActions.addAll(Arrays.asList(nodeActions));
        allActions.addAll(Arrays.asList(super.getActions(arg0)));
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final IProject proj = getLookup().lookup(IProject.class);
        System.out.println("creating property sheet for Project node");

        Property nameProp = new ReadWrite<String>("name", String.class,
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

        Property descProp = new ReadWrite<String>("description", String.class,
                "Project description", "A descripption of this project.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return proj.getDescription();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                proj.setDescription(val);
            }
        };
        set.put(descProp);

        Property ownerProp = new ReadWrite<String>("owner", String.class,
                "Project owner", "The name of this project's owner.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return proj.getOwner();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                proj.setOwner(val);
            }
        };
        set.put(ownerProp);

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
        return getLookup().lookup(IProteomicProject.class).getProjectDirectory().
                getName();
    }
//
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Received change event on ProjectNode: "+evt);
        //getLookup().lookup(IProteomicProject.class).propertyChange(evt);
        //setChildren(Children.create(new ProjectChildNodeFactory(getLookup().lookup(IProteomicProject.class)), true));
    }

//    @Override
//    public void resultChanged(LookupEvent ev) {
//    }
}
