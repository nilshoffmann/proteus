package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.awt.Image;
import java.util.Collection;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author kotte
 */
public class SpotGroupFolderNode extends AbstractNode {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotGroupIcon.png";

    public SpotGroupFolderNode(Collection<ISpotGroup> illg, Lookup lkp) {
        super(Children.create(new SpotGroupFolderChildNodeFactory(illg), true), lkp);
    }

    public SpotGroupFolderNode(Collection<ISpotGroup> illg) {
        super(Children.create(new SpotGroupFolderChildNodeFactory(illg), true), Lookups.singleton(illg));
    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[7];
        nodeActions[0] = CommonProjectActions.newFileAction();
        //List<? extends Action> actions = Utilities.actionsForPath("/Projects/ProteomikLaborProgramm/");
        //List<Action> allActions = Arrays.asList(nodeActions);
        //allActions.addAll(actions);
        //return allActions.toArray(new Action[allActions.size()]);
        return nodeActions;
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
        return "Spot Groups";
    }
}
