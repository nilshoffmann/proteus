package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.CreatePlate384Action;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.CreatePlate96Action;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author kotte
 */
public class ProjectNode extends AbstractNode implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/ProjectIcon.png";

    /**
     *
     * @param ipp
     */
    public ProjectNode(IProteomicProject ipp) {
        this(ipp, new InstanceContent());
    }

    /** A private constructor that takes an InstanceContent and
     * uses it as internals for the Node lookup and also allow us
     * to modify the content, for example by adding a reference
     * to the node itself or any other object we want to represent.
     *
     * @param ipp the IProteomicProject instance
     * @param content the content created by the first constructor
     */
    private ProjectNode(IProteomicProject ipp,  InstanceContent content) {
        super(Children.create(new ProjectChildNodeFactory(ipp), true), new AbstractLookup(content));
        // adds the node to our own lookup
        content.add (this);
        // adds additional items to the lookup
        content.add (ipp);
    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[8];
        CreatePlate96Action cP96Action = new CreatePlate96Action(getLookup().lookup(IProteomicProject.class));
        cP96Action.addPropertyChangeListener(this);
        nodeActions[0] = cP96Action;
        CreatePlate384Action cP384Action = new CreatePlate384Action(getLookup().lookup(IProteomicProject.class));
        cP384Action.addPropertyChangeListener(this);
        nodeActions[1] = cP384Action;
        //nodeActions[0] = CommonProjectActions.newFileAction();
        //nodeActions[1] = CommonProjectActions.copyProjectAction();
        nodeActions[3] = CommonProjectActions.deleteProjectAction();
        nodeActions[5] = CommonProjectActions.setAsMainProjectAction();
        nodeActions[6] = new AbstractAction("Refresh") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                propertyChange(new PropertyChangeEvent(ProjectNode.class,"REFRESH",null,ProjectNode.class));
            }
        };
        nodeActions[7] = CommonProjectActions.closeProjectAction();
        List<? extends Action> actions = Utilities.actionsForPath("/Projects/ProteomikLaborProgramm/");
        List<Action> allActions = Arrays.asList(nodeActions);
        allActions.addAll(actions);
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
        return getLookup().lookup(IProteomicProject.class).getProjectDirectory().getName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        getLookup().lookup(IProteomicProject.class).propertyChange(evt);
        setChildren(Children.create(new ProjectChildNodeFactory(getLookup().lookup(IProteomicProject.class)), true));
    }
}
