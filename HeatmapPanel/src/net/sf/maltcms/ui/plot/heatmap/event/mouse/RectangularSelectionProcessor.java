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
package net.sf.maltcms.ui.plot.heatmap.event.mouse;

import net.sf.maltcms.ui.plot.heatmap.event.AEventProcessor;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author nilshoffmann
 */
public class RectangularSelectionProcessor extends AEventProcessor<Rectangle2D> {

    Point2D selectionStart, selectionEnd;
    
    Rectangle2D result = null;

    @Override
    public void processMouseEvent(MouseEvent me, MouseEventType et) {
        super.processMouseEvent(me, et);
//        if(me.isAltDown() || me.isAltGraphDown() || me.isControlDown() || me.isMetaDown()) {
//            return;
//        }
        boolean changed = false;
        
        switch (et) {
            case PRESSED:
                selectionStart = me.getPoint();
                break;
            case RELEASED:
                selectionStart = null;
                selectionEnd = null;
                result = null;
                changed = true;
                break;
            case DRAGGED:
                selectionEnd = me.getPoint();
                result = new Rectangle2D.Double(selectionStart.getX(), selectionStart.getY(), selectionEnd.getX() - selectionStart.getX(), selectionEnd.getY() - selectionStart.getY());
                changed = true;
                break;
            default:
                break;
        }
        if(changed) {
            notifyListeners();
        }
    }

    @Override
    public Rectangle2D getProcessingResult() {
        return result;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
