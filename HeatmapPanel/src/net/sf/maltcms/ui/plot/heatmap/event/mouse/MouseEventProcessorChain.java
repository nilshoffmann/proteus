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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author nilshoffmann
 */
public class MouseEventProcessorChain implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener {

    private List<IMouseEventProcessor> processorChain;
    private MouseEvent lastMouseEvent;
    private MouseWheelEvent lastMouseWheelEvent;
    private MouseEventType lastMouseEventType;
    private MouseEventType lastMouseWheelEventType;

    public MouseEventProcessorChain(IMouseEventProcessor... imep) {
        this.processorChain = Arrays.asList(imep);
    }

    protected void notify(MouseEvent me, MouseEventType et) {
        lastMouseWheelEvent = null;
//        System.out.println("MouseEvent: "+MouseEvent.getMouseModifiersText(me.getModifiers())+" "+MouseEvent.getMouseModifiersText(me.getModifiersEx()));
        lastMouseEvent = me;
        lastMouseEventType = et;
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            SwingUtilities.invokeLater(this);
        }
    }

    protected void notify(MouseWheelEvent mwe, MouseEventType et) {
        lastMouseEvent = null;
//        System.out.println("MouseWheelEvent: "+MouseEvent.getMouseModifiersText(mwe.getModifiers())+" "+MouseEvent.getMouseModifiersText(mwe.getModifiersEx()));
        lastMouseWheelEvent = mwe;
        lastMouseWheelEventType = et;
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            SwingUtilities.invokeLater(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        notify(me, MouseEventType.CLICKED);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        notify(me, MouseEventType.PRESSED);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        notify(me, MouseEventType.RELEASED);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        notify(me, MouseEventType.ENTERED);
    }

    @Override
    public void mouseExited(MouseEvent me) {
        notify(me, MouseEventType.EXITED);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        notify(me, MouseEventType.DRAGGED);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        notify(me, MouseEventType.MOVED);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        //up, zoom in
        if (mwe.getWheelRotation() < 0) {
            System.out.println("WHEEL_UP");
            notify(mwe, MouseEventType.WHEEL_UP);
        } else {
            System.out.println("WHEEL_DOWN");
            //down, zoom out
            notify(mwe, MouseEventType.WHEEL_DOWN);
        }
    }

    @Override
    public void run() {
        if (lastMouseEvent != null && lastMouseEventType != null) {
            MouseEvent me = lastMouseEvent;
            MouseEventType met = lastMouseEventType;
            for (IMouseEventProcessor imep : processorChain) {
                if (me != null && met != null) {
                    imep.processMouseEvent(me, met);
                }
            }
        }
        if (lastMouseWheelEvent != null && lastMouseWheelEventType != null) {
            MouseWheelEvent mwe = lastMouseWheelEvent;
            MouseEventType mwet = lastMouseWheelEventType;
            for (IMouseEventProcessor imep : processorChain) {
                if (mwe != null && mwet != null) {
                    imep.processMouseWheelEvent(mwe, mwet);
                }
            }
        }

    }
}
