/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.maltcms.ui.plot.heatmap.event.mouse;

/**
 *
 * @author nilshoffmann
 */
public class MouseEvent {

    private final MouseEventType met;
    private final java.awt.event.MouseEvent me;
    
    public MouseEvent(java.awt.event.MouseEvent me, MouseEventType met) {
        this.me = me;
        this.met = met;
    }

    public java.awt.event.MouseEvent getMe() {
        return me;
    }

    public MouseEventType getMet() {
        return met;
    }
}
