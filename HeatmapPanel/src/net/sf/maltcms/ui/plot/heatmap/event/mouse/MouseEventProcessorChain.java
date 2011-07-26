/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    private WeakReference<MouseEvent> lastMouseEvent;
    private WeakReference<MouseWheelEvent> lastMouseWheelEvent;
    private WeakReference<MouseEventType> lastMouseEventType;
    private WeakReference<MouseEventType> lastMouseWheelEventType;

    public MouseEventProcessorChain(IMouseEventProcessor... imep) {
        this.processorChain = Arrays.asList(imep);
    }

    protected void notify(MouseEvent me, MouseEventType et) {
        lastMouseWheelEvent = null;
//        System.out.println("MouseEvent: "+MouseEvent.getMouseModifiersText(me.getModifiers())+" "+MouseEvent.getMouseModifiersText(me.getModifiersEx()));
        lastMouseEvent = new WeakReference<MouseEvent>(me);
        lastMouseEventType = new WeakReference<MouseEventType>(et);
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            SwingUtilities.invokeLater(this);
        }
    }

    protected void notify(MouseWheelEvent mwe, MouseEventType et) {
        lastMouseEvent = null;
//        System.out.println("MouseWheelEvent: "+MouseEvent.getMouseModifiersText(mwe.getModifiers())+" "+MouseEvent.getMouseModifiersText(mwe.getModifiersEx()));
        lastMouseWheelEvent = new WeakReference<MouseWheelEvent>(mwe);
        lastMouseWheelEventType = new WeakReference<MouseEventType>(et);
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
        if(mwe.getWheelRotation()<0) {
            notify(mwe, MouseEventType.WHEEL_UP);
        }else{
            //down, zoom out
            notify(mwe, MouseEventType.WHEEL_DOWN);
        }
    }

    @Override
    public void run() {
        if (lastMouseEvent != null) {
            MouseEvent me = lastMouseEvent.get();
            MouseEventType met = lastMouseEventType.get();
            for (IMouseEventProcessor imep : processorChain) {
                if (me != null && met != null) {
                    imep.processMouseEvent(me, met);
                }
            }
        }
        if (lastMouseWheelEvent != null) {
            MouseWheelEvent mwe = lastMouseWheelEvent.get();
            MouseEventType mwet = lastMouseWheelEventType.get();
            for (IMouseEventProcessor imep : processorChain) {
                if (mwe != null && mwet != null) {
                    imep.processMouseWheelEvent(mwe, mwet);
                }
            }
        }

    }

}
