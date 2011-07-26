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
public class StringEventObjectHandler implements IEventObjectHandler<String, IEventObject<String>> {

    @Override
    public void handle(IEventObject<? super String> ivo) {
        System.out.println("Received String: "+ivo.getCargo());
    }

    @Override
    public Class<? extends String> getEventObjectCargoType() {
        return String.class;
    }

    

}
