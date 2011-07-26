/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maltcms.ui.viewer.datastructures.tree;

import cross.datastructures.tuple.Tuple2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 *
 * @author nilshoffmann
 */
public interface QuadTreeNodeVisitor<T> {

    public LinkedList<Tuple2D<Point2D,T>> visit(LinkedList<Tuple2D<Point2D,T>> l);

}
