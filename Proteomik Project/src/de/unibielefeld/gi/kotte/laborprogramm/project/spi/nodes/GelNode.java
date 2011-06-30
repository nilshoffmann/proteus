package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.Image;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class GelNode extends AbstractNode {

    private IGel gel;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/GelIcon.png";

    public GelNode(IGel gel, Lookup lkp) {
        super(Children.LEAF);
        this.gel = gel;
    }

    public GelNode(IGel gel) {
        super(Children.LEAF);
        this.gel = gel;
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
        return gel.getName();
    }
}
