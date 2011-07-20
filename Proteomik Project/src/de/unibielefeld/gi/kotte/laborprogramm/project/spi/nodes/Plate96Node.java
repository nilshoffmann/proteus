package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
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
public class Plate96Node extends AbstractNode {

    private IPlate96 plate;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/Plate96Icon.png";

    public Plate96Node(IPlate96 plate, Lookup lkp) {
        super(Children.LEAF,new ProxyLookup(lkp,Lookups.fixed(plate,Lookup.getDefault().lookup(IPlate96OpenCookie.class))));
        this.plate = plate;
    }

//    public Plate96Node(IPlate96 plate) {
//        super(Children.LEAF,Lookups.fixed(plate,Lookup.getDefault().lookup(IPlate96OpenCookie.class)));
//        this.plate = plate;
//    }

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
        return plate.getName();
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actions = Utilities.actionsForPath("/Actions/Plate96Node");
//        System.out.println("Retrieved actions for path :"+"/Actions/GelNode/:"+actions);
        List<Action> allActions = new LinkedList<Action>(actions);
        return allActions.toArray(new Action[allActions.size()]);
    }
}
