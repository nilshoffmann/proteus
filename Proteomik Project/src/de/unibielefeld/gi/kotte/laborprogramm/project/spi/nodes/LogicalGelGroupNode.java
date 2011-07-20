package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import java.awt.Image;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author kotte
 */
public class LogicalGelGroupNode extends AbstractNode {

    private ILogicalGelGroup illg;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/LogicalGelGroupIcon.png";

    public LogicalGelGroupNode(ILogicalGelGroup illg, Lookup lkp) {
        super(Children.create(new LogicalGelGroupChildNodeFactory(new ProxyLookup(
                lkp,Lookups.fixed(illg))), true), new ProxyLookup(lkp,Lookups.singleton(illg)));
        this.illg = illg;
    }

//    public LogicalGelGroupNode(ILogicalGelGroup illg) {
//        super(Children.create(new LogicalGelGroupChildNodeFactory(illg), true), Lookups.singleton(illg));
//        this.illg = illg;
//    }

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
        return illg.getName();
    }
}
