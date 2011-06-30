package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.awt.Image;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class SpotGroupNode extends AbstractNode {

    private ISpotGroup isg;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotGroupIcon.png";

    public SpotGroupNode(ISpotGroup isg, Lookup lkp) {
        super(Children.LEAF);
        this.isg = isg;
    }

    public SpotGroupNode(ISpotGroup isg) {
        super(Children.LEAF);
        this.isg = isg;
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
        return "Spot group #" + isg.getNumber();
    }
}
