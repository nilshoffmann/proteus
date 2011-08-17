package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
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
public class TechRepGelGroupNode extends AbstractNode {

    private ITechRepGelGroup itrgg;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/TechRepGelGroupIcon.png";

    public TechRepGelGroupNode(ITechRepGelGroup itrgg, Lookup lkp) {
        super(Children.create(new TechRepGelGroupChildNodeFactory(new ProxyLookup(lkp,Lookups.fixed(itrgg))), true), new ProxyLookup(lkp,Lookups.fixed(itrgg)));
        this.itrgg = itrgg;
    }

//    public TechRepGelGroupNode(ITechRepGelGroup itrgg) {
//        super(Children.create(new TechRepGelGroupChildNodeFactory(itrgg), true), Lookups.singleton(itrgg));
//        this.itrgg = itrgg;
//    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] nodeActions = new Action[7];
        nodeActions[0] = CommonProjectActions.newFileAction();
        List<? extends Action> actions = Utilities.actionsForPath("/Projects/ProteomikLaborProgramm/");
        List<Action> allActions = new ArrayList<Action>(Arrays.asList(nodeActions));
        allActions.addAll(actions);
        allActions.addAll(Arrays.asList(super.getActions(arg0)));
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        System.out.println("creating property sheet for TechRepGelGroup node");

        Property nameProp = new ReadWrite<String>("name", String.class,
                "Group name", "The name of this technical replicates gel group.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return itrgg.getName();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                itrgg.setName(val);
            }
        };
        set.put(nameProp);

        Property descProp = new ReadWrite<String>("description", String.class,
                "Group description", "This gel group's description.") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return itrgg.getDescription();
            }

            @Override
            public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                itrgg.setDescription(val);
            }
        };
        set.put(descProp);

        sheet.put(set);
        return sheet;
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
        return getLookup().lookup(ITechRepGelGroup.class).getName();
    }
}
