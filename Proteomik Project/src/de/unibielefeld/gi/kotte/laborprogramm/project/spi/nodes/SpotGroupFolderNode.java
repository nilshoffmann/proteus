package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport.ReadWrite;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author kotte
 */
public class SpotGroupFolderNode extends AbstractNode {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotGroupIcon.png";

    private SpotGroupFolderChildNodeFactory sgfcnf = new SpotGroupFolderChildNodeFactory();

    private SpotGroupFolderNode(Collection<ISpotGroup> illg, Lookup lkp, SpotGroupFolderChildNodeFactory childNodeFactory) {
        super(Children.create(childNodeFactory,true), new ProxyLookup(lkp,Lookups.fixed(illg)));
        this.sgfcnf = childNodeFactory;
    }

    public SpotGroupFolderNode(Collection<ISpotGroup> illg, Lookup lkp) {
        this(illg,lkp,new SpotGroupFolderChildNodeFactory(new ProxyLookup(lkp,Lookups.fixed(illg.toArray(new ISpotGroup[illg.size()])))));
    }

//    public SpotGroupFolderNode(Collection<ISpotGroup> illg) {
//        super(Children.create(new SpotGroupFolderChildNodeFactory(illg), true), Lookups.singleton(illg));
//    }

    @Override
    public Action[] getActions(boolean arg0) {
        List<? extends Action> actions = Utilities.actionsForPath(
                "/Actions/SpotGroupFolderNode");
        List<Action> allActions = new LinkedList<Action>(actions);
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
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        System.out.println("creating property sheet for SpotGroupFolder node");
        Property sortProp = new ReadWrite<Boolean>("sortSpotGroupsNumerically", Boolean.class,
                "Sort numerically", "Determines whether spot groups are sorted numerically or in order of definition.") {

            @Override
            public Boolean getValue() throws IllegalAccessException, InvocationTargetException {
                return sgfcnf.isSortSpotGroupsNumerically();
            }

            @Override
            public void setValue(Boolean val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                sgfcnf.setSortSpotGroupsNumerically(val);
            }
        };

        set.put(sortProp);

        sheet.put(set);
        return sheet;
    }

    @Override
    public String getDisplayName() {
        return "Spot Groups";
    }
}
