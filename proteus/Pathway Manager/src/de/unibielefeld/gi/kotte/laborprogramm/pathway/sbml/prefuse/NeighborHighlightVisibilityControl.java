package de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.prefuse;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import prefuse.Display;
import prefuse.controls.ControlAdapter;

import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;


/**
 * <p>
 * A ControlListener that sets the highlighted status (using the
 * {@link prefuse.visual.VisualItem#setHighlighted(boolean)
 * VisualItem.setHighlighted} method) for nodes neighboring the node 
 * currently under the mouse pointer. The highlight flag might then be used
 * by a color function to change node appearance as desired.
 * </p>
 *
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class NeighborHighlightVisibilityControl extends ControlAdapter {

    private String activity = null;
    private VisualItem activeItem = null;
    private boolean highlightWithInvisibleEdge = false;
    private boolean changeVisibility = true;
    
    /**
     * Creates a new highlight control.
     */
    public NeighborHighlightVisibilityControl() {
        this(null);
    }
    
    /**
     * Creates a new highlight control that runs the given activity
     * whenever the neighbor highlight changes.
     * @param activity the update Activity to run
     */
    public NeighborHighlightVisibilityControl(String activity) {
        this.activity = activity;
    }
    
    /**
     * @see prefuse.controls.Control#itemEntered(prefuse.visual.VisualItem, java.awt.event.MouseEvent)
     */
    public void itemEntered(VisualItem item, MouseEvent e) {
        Display d = (Display) e.getSource();
        d.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        d.setToolTipText("<html>Name: " + item.getString("name") + "<br>Type: " + item.getString("type") + "<br>Compartment: " + item.getString("compartment_name") + "<br>Degree: " + item.getInt("degree") + "</html>");
//        if (!(item instanceof AggregateItem)) {
//            setFixed(item, true);
//        }
        item.setHover(true);

        if ( activity != null )
            d.getVisualization().run(activity);
//        if ( item instanceof NodeItem)
//            setNeighborHighlight((NodeItem)item, true);
    }
    
    /**
     * @see prefuse.controls.Control#itemExited(prefuse.visual.VisualItem, java.awt.event.MouseEvent)
     */
    public void itemExited(VisualItem item, MouseEvent e) {
        Display d = (Display) e.getSource();
        d.setCursor(Cursor.getDefaultCursor());
        d.setToolTipText(null);

        item.setHover(false);
        
        if ( activity != null )
            d.getVisualization().run(activity);
//        if ( item instanceof NodeItem )
//            setNeighborHighlight((NodeItem)item, false);
    }

    @Override
    public void itemPressed(VisualItem item, MouseEvent me) {
        System.out.println("Received: "+item);
//        if(item != activeItem) {
//            
//        }
//        
//        if(activeItem!=null && activeItem != item) {
//            if(activeItem instanceof NodeItem) {
//                ((NodeItem)item).setFixed(false);
//                setNeighborHighlight((NodeItem)item, false);
//            }
//        }
//        activeItem = item;
//        activeItem.setFixed(true);
        if ( item instanceof NodeItem ) {
            NodeItem ni = (NodeItem)item;
//            if(ni.isInteractive()) {
//               ni.setInteractive(false); 
//            }else{
//               ni.setInteractive(true); 
//            }
            if(ni.isHighlighted()) {
                setNeighborHighlight(ni, false);
            }else{
                setNeighborHighlight(ni, true);
            }
        }
    }

    @Override
    public void itemReleased(VisualItem item, MouseEvent me) {
//        if ( item instanceof NodeItem) {
//            setNeighborHighlight((NodeItem)item, false);
//        }
    }
    
    
    
    /**
     * Set the highlighted state of the neighbors of a node.
     * @param n the node under consideration
     * @param state the highlighting state to apply to neighbors
     */
    protected void setNeighborHighlight(NodeItem n, boolean state) {
        Iterator iter = n.edges();
        n.setHighlighted(state);
        while ( iter.hasNext() ) {
            EdgeItem eitem = (EdgeItem)iter.next();
            NodeItem nitem = eitem.getAdjacentItem(n);
            if (eitem.isVisible() || highlightWithInvisibleEdge) {
                Iterator neighIter = nitem.edges();
                boolean changeVisibility = true;
                while(neighIter.hasNext()) {
                    EdgeItem neitem = (EdgeItem)neighIter.next();
                    if(neitem!=eitem && neitem.isHighlighted()) {
                        changeVisibility = false;
                    }
                }
                if(changeVisibility) {
                    eitem.setHighlighted(state);
                    nitem.setHighlighted(state);
                }
            }
        }
        if ( activity != null )
            n.getVisualization().run(activity);
    }
    
    /**
     * Indicates if neighbor nodes with edges currently not visible still
     * get highlighted.
     * @return true if neighbors with invisible edges still get highlighted,
     * false otherwise.
     */
    public boolean isHighlightWithInvisibleEdge() {
        return highlightWithInvisibleEdge;
    }
   
    /**
     * Determines if neighbor nodes with edges currently not visible still
     * get highlighted.
     * @param highlightWithInvisibleEdge assign true if neighbors with invisible
     * edges should still get highlighted, false otherwise.
     */
    public void setHighlightWithInvisibleEdge(boolean highlightWithInvisibleEdge) {
        this.highlightWithInvisibleEdge = highlightWithInvisibleEdge;
    }
    
} // end of class NeighborHighlightVisibilityControl

