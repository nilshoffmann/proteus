/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.eventObject.factory;

import test.eventObject.DoubleEventObject;
import test.eventObject.IEventObject;

/**
 *
 * @author nilshoffmann
 */
public class DoubleEventObjectFactory implements IEventObjectFactory<Double> {

    @Override
    public IEventObject<Double> create(Double t) {
        return new DoubleEventObject(t);
    }

    @Override
    public Class<? extends Double> getType() {
        return Double.class;
    }

}
