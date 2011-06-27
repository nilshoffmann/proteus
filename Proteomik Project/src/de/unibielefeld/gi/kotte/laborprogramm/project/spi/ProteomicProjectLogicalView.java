package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes.ProjectNode;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.*;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

class ProteomicProjectLogicalView implements LogicalViewProvider {

    private final ProteomicProject project;

    public ProteomicProjectLogicalView(ProteomicProject project) {
        this.project = project;
    }

    @Override
    public org.openide.nodes.Node createLogicalView() {
//        try {
            //Get the Project directory
//            FileObject projDir = project.getProjectDirectory();
//
//            //Get the DataObject that represents it
//            DataFolder textDataObject =
//                    DataFolder.findFolder(projDir);
//
//            //Get its default node-we'll wrap our node around it to change the
//            //display name, icon, etc
//            Node realTextFolderNode = textDataObject.getNodeDelegate();
//
//            //This FilterNode will be our project node
//            return new ProteomicProjectNode(realTextFolderNode, project);

//        } catch (DataObjectNotFoundException donfe) {
//            Exceptions.printStackTrace(donfe);
//            //Fallback-the directory couldn't be created -
//            //read-only filesystem or something evil happened
//            return new AbstractNode(Children.LEAF);
//        }
        return new ProjectNode(project);
    }

    /** This is the node you actually see in the project tab for the project */
//    private static final class ProteomicProjectNode extends FilterNode {
//
//        final ProteomicProject project;
//
//        public ProteomicProjectNode(Node node, ProteomicProject project) throws DataObjectNotFoundException {
//            super(node, new FilterNode.Children(node),
//                    //The projects system wants the project in the Node's lookup.
//                    //NewAction and friends want the original Node's lookup.
//                    //Make a merge of both
//                    new ProxyLookup(new Lookup[]{Lookups.singleton(project),
//                        node.getLookup()
//                    }));
//            this.project = project;
//        }
//
//        @Override
//        public Action[] getActions(boolean arg0) {
//            Action[] nodeActions = new Action[7];
//            nodeActions[0] = CommonProjectActions.newFileAction();
//            nodeActions[1] = CommonProjectActions.copyProjectAction();
//            nodeActions[2] = CommonProjectActions.deleteProjectAction();
//            nodeActions[5] = CommonProjectActions.setAsMainProjectAction();
//            nodeActions[6] = CommonProjectActions.closeProjectAction();
//            List<? extends Action> actions = Utilities.actionsForPath("/Projects/ProteomikLaborProgramm/");
//            List<Action> allActions = Arrays.asList(nodeActions);
//            allActions.addAll(actions);
//            return allActions.toArray(new Action[allActions.size()]);
//        }
//
//        @Override
//        public Image getIcon(int type) {
//            return ImageUtilities.loadImage("de/unibielefeld/gi/kotte/laborprogramm/project/resources/projectIcon.png");
//        }
//
//        @Override
//        public Image getOpenedIcon(int type) {
//            return getIcon(type);
//        }
//
//        @Override
//        public String getDisplayName() {
//            return project.getProjectDirectory().getName();
//        }
//    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
