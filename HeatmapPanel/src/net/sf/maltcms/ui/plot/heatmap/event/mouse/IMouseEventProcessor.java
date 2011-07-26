/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
