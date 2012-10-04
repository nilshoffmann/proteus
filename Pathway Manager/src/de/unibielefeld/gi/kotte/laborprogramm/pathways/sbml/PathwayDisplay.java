package de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml;

import de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml.prefuse.HighlightFilter;
import de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml.prefuse.NeighborHighlightVisibilityControl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import prefuse.action.assignment.FontAction;
import prefuse.action.filter.VisibilityFilter;
import prefuse.action.layout.Layout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.activity.Activity;
import prefuse.activity.ActivityAdapter;
import prefuse.controls.ControlAdapter;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.expression.AndPredicate;
import prefuse.data.expression.OrPredicate;
import prefuse.data.query.ListQueryBinding;
import prefuse.data.query.SearchQueryBinding;
import prefuse.data.search.SearchTupleSet;
import prefuse.data.tuple.TupleSet;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.PolygonRenderer;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.GraphicsLib;
import prefuse.util.UpdateListener;
import prefuse.util.ui.JSearchPanel;
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

    public static int radius = 100;

    /*
     * Color scale from colorbrewer2.org, 12 class, qualitative
     */
    public static final Color[] baseColors = new Color[]{
        new Color(141, 211, 199),
        new Color(255, 255, 179),
        new Color(190, 186, 218),
        new Color(251, 128, 114),
        new Color(128, 177, 211),
        new Color(253, 180, 98),
        new Color(179, 222, 105),
        new Color(252, 205, 229),
        new Color(217, 217, 217),
        new Color(188, 128, 189),
        new Color(204, 235, 197),
        new Color(255, 237, 111)
    };
    public static final Color[] plotColors = new Color[baseColors.length * 2];

    static {
        int cnt = 0;
        for (Color c : baseColors) {
            plotColors[cnt] = c.darker();
            plotColors[baseColors.length + cnt] = c;
            cnt++;
        }
    }

    public static Color withAlpha(Color color, float alpha) {
        Color ca = new Color(color.getRed(), color.getGreen(), color.getBlue(),
                (int) (alpha * 255.0f));
        return ca;
    }

    public static int[] getColorPalette(int elements, float alpha) {
        int[] colors = new int[elements];
        for (int i = 0; i < elements; i++) {
            Color col = withAlpha(plotColors[i % plotColors.length], alpha);
            colors[i] = ColorLib.rgba(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
        }
        return colors;
    }

    public static int[] getDarkerColorPalette(int[] colorPalette) {
        int[] darkerPalette = new int[colorPalette.length];
        for (int i = 0; i < darkerPalette.length; i++) {
            darkerPalette[i] = ColorLib.darker(colorPalette[i]);
        }
        return darkerPalette;
    }

    public static int[] getBrighterColorPalette(int[] colorPalette) {
        int[] brighterPalette = new int[colorPalette.length];
        for (int i = 0; i < brighterPalette.length; i++) {
            brighterPalette[i] = ColorLib.brighter(colorPalette[i]);
        }
        return brighterPalette;
    }

    public PathwayDisplay(SBMLDocument document) {
        //get a display
        super(new Visualization());
        //get data from SBML document
        List<Compartment> compartments = initDataGroups(document);
        int nodes = m_vis.getGroup("graph.nodes").getTupleCount();
        VisualGraph vg = (VisualGraph) m_vis.getVisualGroup("graph");
        System.out.println(vg.getGroup());

        // set up node renderer
        Renderer nodeRenderer = new ShapeRenderer(10);

        // draw aggregates as polygons with curved edges
        Renderer polyR = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
        ((PolygonRenderer) polyR).setCurveSlack(0.0f);

        //set up edge renderer
        EdgeRenderer er = new EdgeRenderer(Constants.EDGE_TYPE_CURVE, Constants.EDGE_ARROW_NONE);
        er.setArrowType(Constants.EDGE_ARROW_FORWARD);
        er.setArrowHeadSize(10, 10);
        er.setRenderType(AbstractShapeRenderer.RENDER_TYPE_DRAW_AND_FILL);
        //add renderers to visualization
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeRenderer);
        drf.setDefaultEdgeRenderer(er);
        drf.add("ingroup('graph.edges')", er);
        drf.add("ingroup('aggregates')", polyR);
        m_vis.setRendererFactory(drf);
        //set up all the color actions
        int[] nodeCompartmentPalette = getColorPalette(compartments.size() + 1, 0.05f);
        int[] nodeCompartmentHighlightPalette = getColorPalette(compartments.size() + 1, 0.25f);
        int[] nodeVisiblePalette = getColorPalette(compartments.size() + 1, 0.1f);
        int[] nodeFixedPalette = getColorPalette(compartments.size() + 1, 0.66f);
        int[] nodeHighlightPalette = getColorPalette(compartments.size() + 1, 0.75f);
        int[] nodeHoverPalette = getColorPalette(compartments.size() + 1, 1.0f);
        int[] edgePalette = new int[]{
            ColorLib.rgba(225, 225, 225, 64),
            ColorLib.rgba(64, 64, 64, 192)
        };

//        DataColorAction aFill = new DataColorAction("aggregates", "id",
//                Constants.NOMINAL, VisualItem.FILLCOLOR, nodeCompartmentPalette);
//        aFill.add(VisualItem.HIGHLIGHT, new DataColorAction("aggregates", "id",
//                Constants.NOMINAL, VisualItem.FILLCOLOR, nodeCompartmentHighlightPalette));
//        aFill.add(VisualItem.HOVER, new DataColorAction("aggregates", "id",
//                Constants.NOMINAL, VisualItem.FILLCOLOR, nodeCompartmentHighlightPalette));

        DataColorAction nodeFill = new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.FILLCOLOR, nodeVisiblePalette);
        nodeFill.add(VisualItem.FIXED, new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.FILLCOLOR, nodeFixedPalette));
        nodeFill.add(VisualItem.HOVER, new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.FILLCOLOR, nodeHoverPalette));
        nodeFill.add(VisualItem.HIGHLIGHT, new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.FILLCOLOR, nodeHighlightPalette));

        DataColorAction nodeStroke = new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.STROKECOLOR, nodeVisiblePalette);
        nodeFill.add(VisualItem.FIXED, new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.STROKECOLOR, getDarkerColorPalette(nodeFixedPalette)));
        nodeStroke.add(VisualItem.HOVER, new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.STROKECOLOR, getDarkerColorPalette(nodeHoverPalette)));
        nodeStroke.add(VisualItem.HIGHLIGHT, new DataColorAction("graph.nodes", "compartment", Constants.NOMINAL, VisualItem.STROKECOLOR, getDarkerColorPalette(nodeHighlightPalette)));

        ColorAction edgeFill = new ColorAction("graph.edges", VisualItem.FILLCOLOR, edgePalette[0]);
        edgeFill.add(VisualItem.FIXED, new ColorAction("graph.edges", VisualItem.FILLCOLOR, edgePalette[0]));
        edgeFill.add(VisualItem.HIGHLIGHT, new ColorAction("graph.edges", VisualItem.FILLCOLOR, edgePalette[1]));
        ColorAction edgeStroke = new ColorAction("graph.edges", VisualItem.STROKECOLOR, edgePalette[0]);
        edgeStroke.add(VisualItem.FIXED, new ColorAction("graph.edges", VisualItem.STROKECOLOR, edgePalette[0]));
        edgeStroke.add(VisualItem.HIGHLIGHT, new ColorAction("graph.edges", VisualItem.STROKECOLOR, edgePalette[1]));

        // bundle the color actions
        ActionList colors = new ActionList();
//        colors.add(aFill);
        colors.add(nodeFill);
        colors.add(nodeStroke);
        colors.add(edgeFill);
        colors.add(edgeStroke);

        ActionList text = new ActionList();
        text.add(new FontAction("graph.nodes", FontLib.getFont("Tahoma", 16)));
        ItemAction textColor = new ColorAction("graph.nodes",
                VisualItem.TEXTCOLOR, ColorLib.rgb(0, 0, 0));
        text.add(textColor);
        m_vis.putAction("text", text);

        // -- search ----------------------------------------------------------

        // zipcode text search is performed using a prefix based search,
        // provided by a search dynamic query. to make this application run
        // more efficiently, we optimize data handling by taking all search
        // results (both added and removed) and shuttling them into a
        // focus set. we work with this reduced set for updating and
        // animating color changes in the action definitions above.

        // create a final reference to the focus set, so that the following
        // search listener can access it.
        final TupleSet focus = m_vis.getFocusGroup(Visualization.FOCUS_ITEMS);
        // create the search query binding
        SearchQueryBinding searchQ = new SearchQueryBinding(vg.getNodes(), "name");
        ListQueryBinding searchCompQ = new ListQueryBinding(vg.getNodes(), "compartment_name");
        AndPredicate filter = new AndPredicate(searchQ.getPredicate());
        filter.add(searchCompQ.getPredicate());
        final SearchTupleSet search = searchQ.getSearchSet();

        ActionList update = new ActionList();
        HighlightFilter hfilter = new HighlightFilter("graph.nodes", filter);
        update.add(hfilter);
        
        UpdateListener lstnr = new UpdateListener() {
            public void update(Object src) {
                m_vis.run("visual");
            }
        };
        filter.addExpressionListener(lstnr);
        
        // create the listener that collects search results into a focus set
        search.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
//                m_vis.cancel("animate");
                System.out.println("Tuple set changed!");
                // invalidate changed tuples, add them all to the focus set
                focus.clear();
                for (int i = 0; i < add.length; ++i) {
                    VisualItem vi = ((VisualItem) add[i]);
                    vi.setFixed(true);
                    focus.addTuple(add[i]);
                    System.out.println("Adding " + add[i].getString("name"));
                }
                for (int i = 0; i < rem.length; ++i) {
                    VisualItem vi = ((VisualItem) rem[i]);
                    vi.setFixed(false);
                    focus.addTuple(rem[i]);
                    System.out.println("Removing " + rem[i].getString("name"));
                }

                m_vis.run("visual");
            }
        });
        m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, search);

        
        
        // create and parameterize a search panel for searching on zip code
        final JSearchPanel searcher = searchQ.createSearchPanel();
        searcher.setLabelText("name>"); // the search box label
        setLayout(new BorderLayout());
        Box searchBox = new Box(BoxLayout.X_AXIS);
        searchBox.add(Box.createHorizontalStrut(5));
        searchBox.add(searcher);
        searchBox.add(Box.createHorizontalGlue());
        searchBox.add(Box.createHorizontalStrut(5));
        searchBox.add(searchCompQ.createComboBox());
        searchBox.add(Box.createHorizontalStrut(16));
        add(searchBox, BorderLayout.SOUTH); // add the search box as a sub-component of the display

        ActionList visual = new ActionList();
        visual.add(update);
        visual.add(colors);
        visual.add(text);
        visual.add(new RepaintAction());
        m_vis.putAction("visual", visual);
        // now create the main layout routine
        ActionList layout = new ActionList();
        layout.add(colors);
        layout.add(text);
        RadialTreeLayout rtl = new RadialTreeLayout("graph", radius);
        Layout l = rtl;
        layout.add(l);
//        layout.add(new AggregateLayout("aggregates"));
        layout.add(new RepaintAction());
        m_vis.putAction("layout", layout);

        // set up the display
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        pan(250, 250);
        setHighQuality(true);
//        addControlListener(new FocusControl(1));
        addControlListener(new AggregateDragControl());
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new WheelZoomControl());
        addControlListener(new NeighborHighlightVisibilityControl("visual"));


        m_vis.runAfter("layout", "visual");
        m_vis.runAfter("visual", "text");
        // set things running
        m_vis.run("layout");
//        m_vis.run("initialVisual");

        ToolTipManager.sharedInstance().setInitialDelay(250);
        ToolTipManager.sharedInstance().setDismissDelay(5000);
        ToolTipManager.sharedInstance().setReshowDelay(0);
    }

    private List<Compartment> initDataGroups(SBMLDocument document) {
        //create graph
        Graph g = new Graph(true);
        g.addColumn("name", String.class);
        g.addColumn("id", int.class);
        g.addColumn("compartment", int.class);
        g.addColumn("compartment_name", String.class);
        g.addColumn("type", String.class);
        g.addColumn("inDegree", int.class);
        g.addColumn("outDegree", int.class);
        g.addColumn("degree", int.class);
        g.addColumn("layout_weight", double.class);

        //create nodes for all species
        ListOf<Species> listOfSpecies = document.getModel().getListOfSpecies();
        final Map<Compartment, Integer> compartmentIdMap = getCompartmentIdMap(document.getModel().getListOfCompartments());
        Collections.sort(listOfSpecies, new Comparator<Species>() {
            @Override
            public int compare(Species t, Species t1) {
                return compartmentIdMap.get(t.getCompartmentInstance()).compareTo(compartmentIdMap.get(t1.getCompartmentInstance()));
            }
        });
        System.out.println("Found " + listOfSpecies.size() + " species.");
        Map<Species, Node> speciesNodes = new HashMap<Species, Node>();
        Map<Node, Species> nodesSpecies = new HashMap<Node, Species>();

        int speciesNr = 0;
        for (Species species : listOfSpecies) {
            Node speciesNode = g.addNode();
            speciesNodes.put(species, speciesNode);
            nodesSpecies.put(speciesNode, species);
            speciesNode.setString("name", species.getName());
            speciesNode.setInt("id", speciesNr++);
            Compartment comp = species.getCompartmentInstance();
            if(comp != null) {
                int value = compartmentIdMap.get(species.getCompartmentInstance()).intValue();
                speciesNode.setInt("compartment", value);
                speciesNode.setString("compartment_name", species.getCompartmentInstance().getName());
            }else{
                speciesNode.setInt("compartment", compartmentIdMap.size());
                speciesNode.setString("compartment_name", "undefined");
            }
            
            speciesNode.setString("type", "species");
            speciesNode.setInt("inDegree", 1);
            speciesNode.setInt("outDegree", 1);
            speciesNode.setInt("degree", 2);
        }
        int[] speciesReactantCounts = new int[listOfSpecies.size()];
        int[] speciesProductCounts = new int[listOfSpecies.size()];
        //create nodes for all reactions
        ListOf<Reaction> listOfReactions = document.getModel().getListOfReactions();
        radius = 100;//listOfReactions.size()+speciesNodes.size();//(int)Math.sqrt((listOfReactions.size()*listOfReactions.size())+(speciesNodes.size()*speciesNodes.size()));
        System.out.println("radius: " + radius);
        System.out.println("Found " + listOfReactions.size() + " reactions.");
        for (Reaction r : listOfReactions) {
            Node reactionNode = g.addNode();
            reactionNode.setString("name", r.getName());
            Compartment comp = r.getCompartmentInstance();
            if (comp != null) {
                int value = compartmentIdMap.get(comp).intValue();
                reactionNode.setInt("compartment", value);
                reactionNode.setString("compartment_name", r.getCompartmentInstance().getName());
            } else {
                reactionNode.setInt("compartment", compartmentIdMap.size());
                reactionNode.setString("compartment_name", "undefined");
            }
            reactionNode.setString("type", "reaction");
            ListOf<SpeciesReference> listOfReactants = r.getListOfReactants();
            reactionNode.setInt("inDegree", listOfReactants.size());
            ListOf<SpeciesReference> listOfProducts = r.getListOfProducts();
            reactionNode.setInt("outDegree", listOfProducts.size());
            reactionNode.setInt("degree", listOfReactants.size() + listOfProducts.size());
            float quotient = ((float) (listOfReactants.size() + listOfProducts.size())) / ((float) listOfReactions.size());
            reactionNode.setDouble("layout_weight", Math.max(1, quotient * (float) radius));
//            reactionNode.setDouble("layout_weight", 2);
//            System.out.println("Reaction node layout_weight: "+reactionNode.getInt("layout_weight"));
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
            float quotient = ((float) (inDegree + outDegree)) / ((float) speciesNodes.size());
            node.setDouble("layout_weight", Math.max(1, quotient * (float) radius));
//            node.setDouble("layout_weight", 2);
//            System.out.println("SpeciesNode layout_weight: "+node.getInt("layout_weight"));
        }

        // add visual data groups
        VisualGraph vg = m_vis.addGraph("graph", g);
//        m_vis.setInteractive("graph.edges", new VisiblePredicate(), false);
        m_vis.setValue("graph.nodes", null, VisualItem.SHAPE,
                new Integer(Constants.SHAPE_ELLIPSE));
        Iterator nodes = vg.nodes();
        int[] nodeFixedPalette = getColorPalette(document.getModel().getListOfCompartments().size() + 1, 0.25f);
        AggregateTable at = m_vis.addAggregates("aggregates");
        at.addColumn(VisualItem.POLYGON, float[].class);
        at.addColumn("id", int.class);
        Map<Integer, AggregateItem> compToAgg = new HashMap<Integer, AggregateItem>();
        while (nodes.hasNext()) {
            VisualItem node = (VisualItem) nodes.next();
            Node original = (Node) node.getSourceTuple();
            node.setSize(node.getInt("layout_weight"));
            Species s = nodesSpecies.get(original);

            // add nodes to aggregates
            // create an aggregate for each 3-clique of nodes
            AggregateItem aitem = null;
            Integer compartmentId = Integer.valueOf(node.getInt("compartment"));
            if (compToAgg.containsKey(compartmentId)) {
                aitem = compToAgg.get(compartmentId);
            } else {
                aitem = (AggregateItem) at.addItem();
                compToAgg.put(compartmentId, aitem);
            }
            aitem.setInt("id", node.getInt("compartment"));
//            System.out.println("Node: "+aitem.getString("name") +" Compartment: "+aitem.getInt("compartment")+" name: "+aitem.getString("compartment_name"));
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
//                node.setString("compartment_name","undefined");
                //exclude hubs
//                if (node.getInt("degree") >= 7) {
//                    node.setVisible(false);
//                }
            }
            aitem.addItem(node);
            node.setFillColor(nodeFixedPalette[node.getInt("compartment")]);
            node.setStrokeColor(ColorLib.darker(nodeFixedPalette[node.getInt("compartment")]));
        }
        m_vis.setValue("graph.edges", null, VisualItem.INTERACTIVE, Boolean.FALSE);
        VisualItem f = (VisualItem) vg.getNode(0);
        Iterator iter = m_vis.getVisualGroup("graph").tuples();
        while(iter.hasNext()) {
            System.out.println(iter.next());
        }
//        m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);
//        f.setFixed(false);
        return document.getModel().getListOfCompartments();
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
    public void itemEntered(VisualItem item, MouseEvent e) {
        Display d = (Display) e.getSource();
        d.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeItem = item;
        d.setToolTipText("<html>Name: " + item.getString("name") + "<br>Type: " + item.getString("type") + "<br>Compartment: " + item.getString("compartment_name") + "<br>Degree: " + item.getInt("degree") + "</html>");
        if (!(item instanceof AggregateItem)) {
            setFixed(item, true);
        }

        d.getVisualization().run("visual");
    }

    /**
     * @see prefuse.controls.Control#itemExited(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    public void itemExited(VisualItem item, MouseEvent e) {
        if (activeItem == item) {
            activeItem = null;
            setFixed(item, false);
        }
        Display d = (Display) e.getSource();
        d.setCursor(Cursor.getDefaultCursor());
        d.setToolTipText(null);

        d.getVisualization().run("visual");
    }

    /**
     * @see prefuse.controls.Control#itemPressed(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
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

        d.getVisualization().run("visual");
    }

    /**
     * @see prefuse.controls.Control#itemReleased(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
    public void itemReleased(VisualItem item, MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        if (dragged) {
            activeItem = null;
            setFixed(item, false);
            dragged = false;
        }
        ((Display) e.getComponent()).getVisualization().run("visual");
    }

    /**
     * @see prefuse.controls.Control#itemDragged(prefuse.visual.VisualItem,
     * java.awt.event.MouseEvent)
     */
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

        d.getVisualization().run("visual");
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
            double x = item.getX();
            double y = item.getY();
            item.setStartX(x);
            item.setStartY(y);
            item.setX(x + dx);
            item.setY(y + dy);
            item.setEndX(x + dx);
            item.setEndY(y + dy);
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