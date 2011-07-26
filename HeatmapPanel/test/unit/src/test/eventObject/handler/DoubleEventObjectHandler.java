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
public class DoubleEventObjectHandler implements IEventObjectHandler<Double, IEventObject<Double>> {

    @Override
    public void handle(IEventObject<? super Double> ivo) {
        System.out.println("Received Double: "+ivo.getCargo());
    }

    @Override
    public Class<? extends Double> getEventObjectCargoType() {
        return Double.class;
    }

}
