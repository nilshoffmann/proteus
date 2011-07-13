package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.CreatePlate384Action;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.CreatePlate96Action;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
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
    private final InstanceContent lookupContents;

    public ProjectNode() {
        this(new InstanceContent());
    }

    private ProjectNode(Lookup lkp) {
        this(new InstanceContent(),lkp);
    }

    private ProjectNode(InstanceContent ic) {
        super(Children.LEAF, new AbstractLookup(ic));
        this.lookupContents = ic;
    }

    private ProjectNode(InstanceContent ic, Lookup lkp) {
        super(Children.LEAF, new ProxyLookup(lkp,new AbstractLookup(ic)));
        this.lookupContents = ic;
    }

    public ProjectNode(IProteomicProject ipp, Lookup lkp) {
        this(lkp);
        setChildren(Children.create(new ProjectChildNodeFactory(ipp), true));
        lookupContents.add(ipp);
    }
    
    public ProjectNode(IProteomicProject ipp) {
        this();
        setChildren(Children.create(new ProjectChildNodeFactory(ipp), true));
        lookupContents.add(ipp);
    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[7];
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
        nodeActions[6] = CommonProjectActions.closeProjectAction();
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
        setChildren(Children.create(new ProjectChildNodeFactory(getLookup().lookup(IProteomicProject.class)), true));
    }
}
