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
package net.sf.maltcms.ui.plot.heatmap.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.IMouseEventProcessor;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventType;

/**
 * Proxy class for mouse and key events. 
 * 
 * @author nilshoffmann
 */
public abstract class AEventProcessor<T> implements IMouseEventProcessor<T>, KeyListener {

    private Map<Long, List<IProcessorResultListener<T>>> listeners = new HashMap<Long, List<IProcessorResultListener<T>>>();
    private long exMask = -1l;
    private net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent et = null;

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void addListener(IProcessorResultListener<T> iprl) {
        addListener(iprl, -1);
    }

    @Override
    public void addListener(IProcessorResultListener<T> iprl, long exMask) {
//        System.out.println("Registering listener "+iprl.getClass().getName()+" for mask: "+exMask);
        List<IProcessorResultListener<T>> l = null;
        Long key = Long.valueOf(exMask);
        if (listeners.containsKey(key)) {
            l = listeners.get(key);
            l.add(iprl);
        } else {
            l = new LinkedList<IProcessorResultListener<T>>();
            l.add(iprl);
            listeners.put(key, l);
        }
    }

    @Override
    public void removeListener(IProcessorResultListener<T> iprl, long exMask) {
        List<IProcessorResultListener<T>> l = null;
        Long key = Long.valueOf(exMask);
        if (listeners.containsKey(key)) {
            l = listeners.get(key);
            l.remove(iprl);
        }
    }

    @Override
    public void removeListener(IProcessorResultListener<T> iprl) {
        removeListener(iprl, -1);
    }

    @Override
    public void processMouseEvent(MouseEvent me, MouseEventType et) {
        exMask = me.getModifiersEx();
        this.et = new net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent(me, et);
        //System.out.println("exMask: "+exMask);
    }

    @Override
    public void processMouseWheelEvent(MouseWheelEvent mwe, MouseEventType et) {
        exMask = mwe.getModifiersEx();
        this.et = new net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent(mwe, et);
//        this.et = et;
    }

    @Override
    public void run() {
        if (listeners.isEmpty()) {
            return;
        }
        if (listeners.containsKey(-1l)) {
            //always notify listeners registered for -1
//            System.out.println("Notifying "+listeners.get(Integer.valueOf(-1)).size() +" listeners for ALL events");
            for (IProcessorResultListener<T> iprl : listeners.get(Long.valueOf(-1))) {
                iprl.listen(getProcessingResult(),et);
            }
        }
        if (listeners.containsKey(exMask)) {
//            System.out.println("Notifying "+listeners.get(Integer.valueOf(exMask)).size() +" listeners for mask: "+exMask);
            for (IProcessorResultListener<T> iprl : listeners.get(Long.valueOf(exMask))) {
                iprl.listen(getProcessingResult(),et);
            }
        }
    }

    @Override
    public void notifyListeners() {
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            SwingUtilities.invokeLater(this);
        }
    }
}
