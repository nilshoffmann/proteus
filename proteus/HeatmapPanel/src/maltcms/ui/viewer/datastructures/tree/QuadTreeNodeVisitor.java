/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 * Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 */
package maltcms.ui.viewer.datastructures.tree;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import net.sf.maltcms.ui.plot.heatmap.Tuple2D;

/**
 *
 * @author nilshoffmann
 */
public interface QuadTreeNodeVisitor<T> {

    public LinkedList<Tuple2D<Point2D,T>> visit(LinkedList<Tuple2D<Point2D,T>> l);

}
