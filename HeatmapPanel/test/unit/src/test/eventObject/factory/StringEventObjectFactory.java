/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.eventObject.factory;

import test.eventObject.IEventObject;
import test.eventObject.StringEventObject;

/**
 *
 * @author nilshoffmann
 */
public class StringEventObjectFactory implements IEventObjectFactory<String> {

    @Override
    public IEventObject<String> create(String t) {
        return new StringEventObject(t);
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

}
