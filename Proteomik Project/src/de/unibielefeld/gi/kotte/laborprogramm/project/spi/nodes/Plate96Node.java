package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.awt.Image;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class Plate96Node extends AbstractNode {

    private IPlate96 plate;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/Plate96Icon.png";

    public Plate96Node(IPlate96 plate, Lookup lkp) {
        super(Children.LEAF);
        this.plate = plate;
    }

    public Plate96Node(IPlate96 plate) {
        super(Children.LEAF);
        this.plate = plate;
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
        return plate.getName();
    }
}
