/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.event;

//import MouseEvent;

import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;


/**
 *
 * @author nilshoffmann
 */
public interface IProcessorResultListener<T> {

    void listen(T t, MouseEvent me);

}
