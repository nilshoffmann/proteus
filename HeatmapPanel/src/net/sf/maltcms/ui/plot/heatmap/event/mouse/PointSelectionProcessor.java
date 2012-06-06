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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 *
 * @author nilshoffmann
 */
public class PointSelectionProcessor extends AEventProcessor<Point2D> {

    Point2D result;

    @Override
    public void processMouseEvent(MouseEvent me, MouseEventType et) {
        super.processMouseEvent(me, et);
//        boolean isShiftDown = me.isShiftDown();

        boolean changed = false;
        switch (et) {
            default:
                result = me.getPoint();
                changed = true;
                break;
        }
        if (changed) {
//            System.out.println("PointSelectionProcessor notifying listeners for mouse event!");
            notifyListeners();
        }
    }

    @Override
    public Point2D getProcessingResult() {
        return result;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
//        System.out.println("Received key typed event "+ke.toString());
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("Received key pressed event "+ke.toString());
        if (result != null) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_UP:
                    result.setLocation(result.getX(), result.getY() - 1.0d);
                    break;
                case KeyEvent.VK_DOWN:
                    result.setLocation(result.getX(), result.getY() + 1.0d);
                    break;
                case KeyEvent.VK_LEFT:
                    result.setLocation(result.getX() - 1, result.getY());
                    break;
                case KeyEvent.VK_RIGHT:
                    result.setLocation(result.getX() + 1, result.getY());
                    break;
                default:
            }
//            System.out.println("PointSelectionProcessor notifying listeners for key event!");
            notifyListeners();
        }
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
//        System.out.println("Received key released event "+ke.toString());
//        if (result != null) {
//            switch (ke.getKeyCode()) {
//                case KeyEvent.VK_UP:
//                    result.setLocation(result.getX(), result.getY() - 1.0d);
//                    break;
//                case KeyEvent.VK_DOWN:
//                    result.setLocation(result.getX(), result.getY() + 1.0d);
//                    break;
//                case KeyEvent.VK_LEFT:
//                    result.setLocation(result.getX() - 1, result.getY());
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    result.setLocation(result.getX() + 1, result.getY());
//                    break;
//                default:
//            }
//            notifyListeners();
//        }
    }
}
