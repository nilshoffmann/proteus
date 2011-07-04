package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
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
public class GelNode extends AbstractNode {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/GelIcon.png";

    public GelNode(IGel gel, Lookup lkp) {
        super(Children.LEAF,new ProxyLookup(lkp,Lookups.fixed(gel,Lookup.getDefault().lookup(IGelOpenCookie.class))));
    }

    public GelNode(IGel gel) {
        super(Children.LEAF,Lookups.fixed(gel,Lookup.getDefault().lookup(IGelOpenCookie.class)));
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
        return getLookup().lookup(IGel.class).getName();
    }

    @Override
    public Action[] getActions(boolean arg0) {
        //Action[] nodeActions = new Action[7];
        //nodeActions[0] = CommonProjectActions.newFileAction();
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/GelNode");
        System.out.println("Retrieved actions for path :"+"/Actions/GelNode/:"+actions);
        List<Action> allActions = new LinkedList<Action>(actions);
        //allActions.addAll(Arrays.asList(nodeActions));
        return allActions.toArray(new Action[allActions.size()]);
    }
}
