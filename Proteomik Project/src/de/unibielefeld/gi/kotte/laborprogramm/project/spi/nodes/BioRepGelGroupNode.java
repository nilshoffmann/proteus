package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author kotte
 */
public class BioRepGelGroupNode extends AbstractNode {

    private IBioRepGelGroup ibrgg;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/BioRepGelGroupIcon.png";

    public BioRepGelGroupNode(IBioRepGelGroup ibrgg, Lookup lkp) {
        super(Children.create(new BioRepGelGroupChildNodeFactory(new ProxyLookup(lkp,Lookups.fixed(ibrgg))), true), new ProxyLookup(lkp,Lookups.singleton(ibrgg)));
        this.ibrgg = ibrgg;
    }

//    public BioRepGelGroupNode(IBioRepGelGroup ibrgg) {
//        super(Children.create(new BioRepGelGroupChildNodeFactory(ibrgg), true), Lookups.singleton(ibrgg));
//        this.ibrgg = ibrgg;
//    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[7];
        nodeActions[0] = CommonProjectActions.newFileAction();
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/ProteomikLaborProgramm/"+getClass().getSimpleName()+"/");
        List<Action> allActions = new LinkedList<Action>(actions);
        allActions.addAll(Arrays.asList(nodeActions));
        return allActions.toArray(new Action[allActions.size()]);
//        return nodeActions;
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
        return ibrgg.getName();
    }
}
