/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.eventObject;

/**
 *
 * @author nilshoffmann
 */
public interface IEventObject<T> extends Comparable<IEventObject> {

    T getCargo();

    Class<? extends T> getCargoType();

    long getWhen();
    
}
