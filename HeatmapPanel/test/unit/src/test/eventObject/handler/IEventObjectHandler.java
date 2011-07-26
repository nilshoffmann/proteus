/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.eventObject.handler;

import test.eventObject.IEventObject;

/**
 *
 * @author nilshoffmann
 */
public interface IEventObjectHandler<T,U extends IEventObject<T>> {

    void handle(IEventObject<? super T> ieo);
    
    Class<? extends T> getEventObjectCargoType();
    
}
