package de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataSizeAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.layout.Layout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.action.layout.graph.FruchtermanReingoldLayout;
import prefuse.activity.Activity;
import prefuse.controls.ControlAdapter;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.GraphicsLib;
import prefuse.visual.AggregateItem;
import prefuse.visual.AggregateTable;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

/**
 * The frame that displays the prefuse graph of a pathway.
 *
 * @author kotte
 *
 * Based on the prefuse demo for aggregated view. Copyright (c) 2004-2006
 * Regents of the University of California.
 *
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class PathwayDisplay extends Display {

    public PathwayDisplay(SBMLDocument document) {
        //get a display
        super(new Visualization());
        //get data from SBML document
        initDataGroups(document);
        // set up node renderer
        Renderer nodeRenderer = new ShapeRenderer(10);
        //set up edge renderer
        EdgeRenderer er = new EdgeRenderer(Constants.EDGE_TYPE_CURVE, Constants.EDGE_ARROW_NONE);
        er.setArrowType(Constants.EDGE_ARROW_FORWARD);
        er.setArrowHeadSize(10, 10);
        er.setRenderType(AbstractShapeRenderer.RENDER_TYPE_DRAW_AND_FILL);
        //add renderers to visualization
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeRenderer);
        drf.add("ingroup('graph.edges')", er);
        m_vis.setRendererFactory(drf);
        //set up all the color actions
        ColorAction nStroke = new ColorAction("graph.nodes", VisualItem.STROKECOLOR);
        nStroke.setDefaultColor(ColorLib.gray(100));
        ColorAction nFill = new ColorAction("graph.nodes", VisualItem.FILLCOLOR);
        nFill.setDefaultColor(ColorLib.gray(255));
        nFill.add("_hover", ColorLib.gray(200));
        ColorAction nEdges = new ColorAction("graph.edges", VisualItem.STROKECOLOR);
        nEdges.setDefaultColor(ColorLib.gray(100));
        ColorAction nEdgesFill = new ColorAction("graph.edges", VisualItem.FILLCOLOR);
        nEdgesFill.setDefaultColor(ColorLib.gray(100));
        int[] palette = new int[]{
            ColorLib.rgba(200, 255, 200, 150),
            ColorLib.rgba(255, 200, 200, 150)//,
//            ColorLib.rgba(200, 200, 255, 150)
        };
        ColorAction fill = new ColorAction("graph.nodes", VisualItem.FILLCOLOR, ColorLib.rgb(200, 200, 225));
        fill.add(VisualItem.FIXED, new DataColorAction("graph.nodes", "compartment",
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette));
        fill.add(VisualItem.HIGHLIGHT, new DataColorAction("graph.nodes", "compartment",
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette));
        // bundle the color actions
        ActionList colors = new ActionList();
        colors.add(nStroke);
        colors.add(nFill);
        colors.add(nEdges);
        colors.add(nEdgesFill);
        colors.add(fill);
        ActionList text = new ActionList();
        text.add(new FontAction("graph.nodes", FontLib.getFont("Tahoma", 16)));
        ItemAction textColor = new ColorAction("graph.nodes",
                VisualItem.TEXTCOLOR, ColorLib.rgb(0, 0, 0));
        text.add(textColor);
        m_vis.putAction("text", text);

        ActionList visual = new ActionList(Activity.INFINITY);
        visual.add(colors);
        visual.add(text);
        visual.add(new DataSizeAction("graph.nodes", "degree"));
        visual.add(new RepaintAction());
        m_vis.putAction("visual", visual);
        // now create the main layout routine
//        ActionList layout = new ActionList(Activity.INFINITY);
        ActionList layout = new ActionList();
        layout.add(colors);
        layout.add(text);
        FruchtermanReingoldLayout frl = new FruchtermanReingoldLayout("graph");
        frl.setMaxIterations(2000);
        Layout l = frl;//new ForceDirectedLayout("graph", false);
        layout.add(l);
//        layout.add(new AggregateLayout("aggregates"));
        layout.add(new RepaintAction());
        m_vis.putAction("layout", layout);

        // set up the display
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        pan(250, 250);
        setHighQuality(true);
        addControlListener(new AggregateDragControl());
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new NeighborHighlightControl());

        // set things running
        m_vis.run("layout");
        m_vis.runAfter("layout", "visual");
        m_vis.runAfter("visual", "text");
        ToolTipManager.sharedInstance().setInitialDelay(250);
        ToolTipManager.sharedInstance().setDismissDelay(5000);
        ToolTipManager.sharedInstance().setReshowDelay(0);
    }

    private void initDataGroups(SBMLDocument document) {
        //create graph
        Graph g = new Graph(true);
        g.addColumn("name", String.class);
        g.addColumn("id", int.class);
        g.addColumn("compartment", int.class);
        g.addColumn("inDegree", int.class);
        g.addColumn("outDegree", int.class);
        g.addColumn("degree", int.class);
        //create nodes for all species
        ListOf<Species> listOfSpecies = document.getModel().getListOfSpecies();
        System.out.println("Found " + listOfSpecies.size() + " species.");
        Map<Species, Node> speciesNodes = new HashMap<Species, Node>();
        Map<Node, Species> nodesSpecies = new HashMap<Node, Species>();
        Map<Compartment, Integer> compartmentIdMap = getCompartmentIdMap(document.getModel().getListOfCompartments());
        int speciesNr = 0;
        for (Species species : listOfSpecies) {
            Node speciesNode = g.addNode();
            speciesNodes.put(species, speciesNode);
            nodesSpecies.put(speciesNode, species);
            speciesNode.setString("name", species.getName());
            speciesNode.setInt("id", speciesNr++);
            speciesNode.setInt("compartment", compartmentIdMap.get(species.getCompartmentInstance()));
            speciesNode.setInt("inDegree", 1);
            speciesNode.setInt("outDegree", 1);
            speciesNode.setInt("degree", 2);
        }
        int[] speciesReactantCounts = new int[listOfSpecies.size()];
        int[] speciesProductCounts = new int[listOfSpecies.size()];
        //create nodes for all reactions
        ListOf<Reaction> listOfReactions = document.getModel().getListOfReactions();
        System.out.println("Found " + listOfReactions.size() + " reactions.");
        for (Reaction r : listOfReactions) {
            Node reactionNode = g.addNode();
            reactionNode.setString("name", r.getName());
            reactionNode.setInt("compartment", 0);
            ListOf<SpeciesReference> listOfReactants = r.getListOfReactants();
            reactionNode.setInt("inDegree", listOfReactants.size());
            ListOf<SpeciesReference> listOfProducts = r.getListOfProducts();
            reactionNode.setInt("outDegree", listOfProducts.size());
            reactionNode.setInt("degree", listOfReactants.size() + listOfProducts.size());
            //create an edge from each reactant...
            for (SpeciesReference reactant : listOfReactants) {
                Species r_species = reactant.getSpeciesInstance();
                //... to each product
                Node speciesNode = speciesNodes.get(r_species);
                speciesReactantCounts[speciesNode.getInt("id")]++;
                g.addEdge(speciesNode, reactionNode);
            }
            for (SpeciesReference product : listOfProducts) {
                Species p_species = product.getSpeciesInstance();
                Node speciesNode = speciesNodes.get(p_species);
                speciesProductCounts[speciesNode.getInt("id")]++;
                g.addEdge(reactionNode, speciesNode);
            }
        }

        for (Species s : speciesNodes.keySet()) {
            Node node = speciesNodes.get(s);
            int inDegree = speciesReactantCounts[node.getInt("id")];
            int outDegree = speciesProductCounts[node.getInt("id")];
            node.setInt("inDegree", inDegree);
            node.setInt("outDegree", outDegree);
            node.setInt("degree", inDegree + outDegree);
        }

        // add visual data groups
        VisualGraph vg = m_vis.addGraph("graph", g);
        m_vis.setInteractive("graph.edges", null, false);
        m_vis.setValue("graph.nodes", null, VisualItem.SHAPE,
                new Integer(Constants.SHAPE_ELLIPSE));
        Iterator nodes = vg.nodes();
        while (nodes.hasNext()) {
            VisualItem node = (VisualItem) nodes.next();
            Node original = (Node) node.getSourceTuple();
            Species s = nodesSpecies.get(original);
            if (s != null) {
                //species node
                node.setShape(Constants.SHAPE_ELLIPSE);
                //exclude hubs
//                if (node.getInt("degree") >= 11) {
//                    node.setVisible(false);
//                }
            } else {
                // reaction node
                node.setShape(Constants.SHAPE_RECTANGLE);
                node.setFillColor(ColorLib.getColor(0, 255, 0, 64).getRGB());
                //exclude hubs
//                if (node.getInt("degree") >= 7) {
//                    node.setVisible(false);
//                }
            }
        }
    }

    private Map<Compartment, Integer> getCompartmentIdMap(ListOf<Compartment> compartments) {
        HashMap<Compartment, Integer> idMap = new HashMap<Compartment, Integer>();
        for (int i = 0; i < compartments.size(); i++) {
            System.out.println("Adding compartment " + compartments.get(i) + " with id " + i);
            idMap.put(compartments.get(i), Integer.valueOf(i));
        }
        return idMap;
    }
}

/**
 * Layout algorithm that computes a convex hull surrounding aggregate items and
 * saves it in the "_polygon" field.
 */
class AggregateLayout extends Layout {

    private int m_hullPxlMargin = 5; // convex hull pixel margin
    private double[] m_pts;   // buffer for computing convex hulls

    public AggregateLayout(String aggrGroup) {
        super(aggrGroup);
    }

    /**
     * @see
     * edu.berkeley.guir.prefuse.action.Action#run(edu.berkeley.guir.prefuse.ItemRegistry,
     * double)
     */
    @Override
    public void run(double frac) {

        AggregateTable aggr = (AggregateTable) m_vis.getGroup(m_group);
        // do we have any  to process?
        int num = aggr.getTupleCount();
        if (num == 0) {
            return;
        }

        // update buffers
        int maxsz = 0;
        for (Iterator aggrs = aggr.tuples(); aggrs.hasNext();) {
            maxsz = Math.max(maxsz, 4 * 2
                    * ((AggregateItem) aggrs.next()).getAggregateSize());
        }
        if (m_pts == null || maxsz > m_pts.length) {
            m_pts = new double[maxsz];
        }

        // compute and assign convex hull for each aggregate
        Iterator aggrs = m_vis.visibleItems(m_group);
        while (aggrs.hasNext()) {
            AggregateItem aitem = (AggregateItem) aggrs.next();

            int idx = 0;
            if (aitem.getAggregateSize() == 0) {
                continue;
            }
            VisualItem item;
            Iterator iter = aitem.items();
            while (iter.hasNext()) {
                item = (VisualItem) iter.next();
                if (item.isVisible()) {
                    addPoint(m_pts, idx, item, m_hullPxlMargin);
                    idx += 2 * 4;
                }
            }
            // if no aggregates are visible, do nothing
            if (idx == 0) {
                continue;
            }

            // compute convex hull
            double[] nhull = GraphicsLib.convexHull(m_pts, idx);

            // prepare viz attribute array
            float[] fhull = (float[]) aitem.get(VisualItem.POLYGON);
            if (fhull == null || fhull.length < nhull.length) {
                fhull = new float[nhull.length];
            } else if (fhull.length > nhull.length) {
                fhull[nhull.length] = Float.NaN;
            }

            // copy hull values
            for (int j = 0; j < nhull.length; j++) {
                fhull[j] = (float) nhull[j];
            }
            aitem.set(VisualItem.POLYGON, fhull);
            aitem.setValidated(false); // force invalidation
        }
    }

    private static void addPoint(double[] pts, int idx,
            VisualItem item, int growth) {
        Rectangle2D b = item.getBounds();
        double minX = (b.getMinX()) - growth, minY = (b.getMinY()) - growth;
        double maxX = (b.getMaxX()) + growth, maxY = (b.getMaxY()) + growth;
        pts[idx] = minX;
        pts[idx + 1] = minY;
        pts[idx + 2] = minX;
        pts[idx + 3] = maxY;
        pts[idx + 4] = maxX;
        pts[idx + 5] = minY;
        pts[idx + 6] = maxX;
        pts[idx + 7] = maxY;
    }
} // end of class AggregateLayout

/**
 * Interactive drag control that is "aggregate-aware"
 */
class AggregateDragControl extends ControlAdapter {

    private VisualItem activeItem;
    protected Point2D down = new Point2D.Double();
    protected Point2D temp = new Point2D.Double();
    protected boolean dragged;

    /**
     * Creates a new drag control that issues repaint requests as an item is
     * dragged.
     */
    public AggregateDragControl() {
    }

    /**
     * @see prefuse.controls.Control#itemEntered(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    @Override
    public void itemEntered(VisualItem item, MouseEvent e) {
        Display d = (Display) e.getSource();
        d.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeItem = item;
        
        d.setToolTipText("<html>"+item.getString("name")+"</html>");
        if (!(item instanceof AggregateItem)) {
            setFixed(item, true);
        }
        
    }

    /**
     * @see prefuse.controls.Control#itemExited(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    @Override
    public void itemExited(VisualItem item, MouseEvent e) {
        if (activeItem == item) {
            activeItem = null;
            setFixed(item, false);
        }
        Display d = (Display) e.getSource();
        d.setCursor(Cursor.getDefaultCursor());
        d.setToolTipText(null);
    }

    /**
     * @see prefuse.controls.Control#itemPressed(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    @Override
    public void itemPressed(VisualItem item, MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        dragged = false;
        Display d = (Display) e.getComponent();
        d.getAbsoluteCoordinate(e.getPoint(), down);
        if (item instanceof AggregateItem) {
            setFixed(item, true);
        }
    }

    /**
     * @see prefuse.controls.Control#itemReleased(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    @Override
    public void itemReleased(VisualItem item, MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        if (dragged) {
            activeItem = null;
            setFixed(item, false);
            dragged = false;
        }
    }

    /**
     * @see prefuse.controls.Control#itemDragged(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    @Override
    public void itemDragged(VisualItem item, MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        dragged = true;
        Display d = (Display) e.getComponent();
        d.getAbsoluteCoordinate(e.getPoint(), temp);
        double dx = temp.getX() - down.getX();
        double dy = temp.getY() - down.getY();

        move(item, dx, dy);

        down.setLocation(temp);
    }

    protected static void setFixed(VisualItem item, boolean fixed) {
        if (item instanceof AggregateItem) {
            Iterator items = ((AggregateItem) item).items();
            while (items.hasNext()) {
                setFixed((VisualItem) items.next(), fixed);
            }
        } else {
            item.setFixed(fixed);
        }
    }

    protected static void move(VisualItem item, double dx, double dy) {
        if (item instanceof AggregateItem) {
            Iterator items = ((AggregateItem) item).items();
            while (items.hasNext()) {
                move((VisualItem) items.next(), dx, dy);
            }
        } else {
            double x = item.getX();
            double y = item.getY();
            item.setStartX(x);
            item.setStartY(y);
            item.setX(x + dx);
            item.setY(y + dy);
            item.setEndX(x + dx);
            item.setEndY(y + dy);
        }
    }
} // end of class AggregateDragControl