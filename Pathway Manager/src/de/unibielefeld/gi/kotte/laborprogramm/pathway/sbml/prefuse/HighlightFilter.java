package de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.prefuse;

import java.util.Iterator;

import prefuse.Visualization;
import prefuse.action.GroupAction;
import prefuse.data.expression.OrPredicate;
import prefuse.data.expression.Predicate;
import prefuse.util.PrefuseLib;
import prefuse.visual.VisualItem;

/**
 * Filter Action that sets visible all items that meet a given Predicate
 * condition and sets all other items invisible.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class HighlightFilter extends GroupAction {
    
    private Predicate m_filter;
    private Predicate m_predicate;
    
    /**
     * Create a new VisibilityFilter.
     * @param p the test predicate used to determine visibility
     */
    public HighlightFilter(Predicate p) {
        setPredicate(p);
    }

    /**
     * Create a new VisibilityFilter.
     * @param group the data group to process
     * @param p the test predicate used to determine visibility
     */
    public HighlightFilter(String group, Predicate p) {
        super(group);
        setPredicate(p);
    }

    /**
     * Create a new VisibilityFilter.
     * @param vis the Visualization to process
     * @param group the data group to process
     * @param p the test predicate used to determine visibility
     */
    public HighlightFilter(Visualization vis, String group, Predicate p) {
        super(vis, group);
        setPredicate(p);
    }

    /**
     * Set the test predicate used to determine visibility.
     * @param p the test predicate to set
     */
    protected void setPredicate(Predicate p) {
        m_predicate = p;
        m_filter = new OrPredicate(p, HighlightPredicate.TRUE);
    }
    
    /**
     * @see prefuse.action.Action#run(double)
     */
    public void run(double frac) {
        Iterator items = m_vis.items(m_group, m_filter);
        while ( items.hasNext() ) {
            VisualItem item = (VisualItem)items.next();
//            PrefuseLib.updateVisible(item, m_predicate.getBoolean(item));
            item.setFixed(m_predicate.getBoolean(item));
        }
    }

} // end of class VisibilityAction
