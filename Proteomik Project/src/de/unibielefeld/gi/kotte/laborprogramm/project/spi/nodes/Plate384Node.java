package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.awt.Image;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class Plate384Node extends AbstractNode {

    private IPlate384 plate;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/Plate384Icon.png";

    public Plate384Node(IPlate384 plate, Lookup lkp) {
        super(Children.LEAF);
        this.plate = plate;
    }

    public Plate384Node(IPlate384 plate) {
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