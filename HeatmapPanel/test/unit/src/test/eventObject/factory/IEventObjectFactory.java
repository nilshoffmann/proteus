/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.eventObject.factory;

import test.eventObject.IEventObject;

/**
 *
 * @author nilshoffmann
 */
public interface IEventObjectFactory<T> {

    IEventObject<T> create(T t);
    
    Class<? extends T> getType();
}
