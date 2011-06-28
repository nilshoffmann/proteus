package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author kotte
 */
public class ProjectNode extends AbstractNode{

    private IProteomicProject ipp;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/ProjectIcon.png";

    public ProjectNode(IProteomicProject ipp, Lookup lkp) {
        super(Children.create(new ProjectChildNodeFactory(ipp),true),lkp);
        this.ipp = ipp;
    }

    public ProjectNode(IProteomicProject ipp) {
        super(Children.create(new ProjectChildNodeFactory(ipp),true),Lookups.singleton(ipp));
        this.ipp = ipp;
    }

    @Override
        public Action[] getActions(boolean arg0) {
            Action[] nodeActions = new Action[7];
            nodeActions[0] = CommonProjectActions.newFileAction();
            nodeActions[1] = CommonProjectActions.copyProjectAction();
            nodeActions[2] = CommonProjectActions.deleteProjectAction();
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
            return ipp.getProjectDirectory().getName();
        }

}
