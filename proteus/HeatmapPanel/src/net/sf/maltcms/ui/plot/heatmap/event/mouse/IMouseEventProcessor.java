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
import java.awt.event.MouseWheelEvent;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;

/**
 *
 * @author nilshoffmann
 */
public interface IMouseEventProcessor<T> extends Runnable {

    void processMouseWheelEvent(MouseWheelEvent mwe, MouseEventType et);
    
    void processMouseEvent(MouseEvent me, MouseEventType et);
    
    void addListener(IProcessorResultListener<T> iprl);
    
    void addListener(IProcessorResultListener<T> iprl, long exMask);
    
    void removeListener(IProcessorResultListener<T> iprl);
    
    void removeListener(IProcessorResultListener<T> iprl, long exMask);
    
    void notifyListeners();
    
    T getProcessingResult();
    
}
