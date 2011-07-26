/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import test.eventObject.handler.StringEventObjectHandler;
import test.eventObject.handler.DoubleEventObjectHandler;

/**
 *
 * @author nilshoffmann
 */
public class EventGenerator {

    public static void main(String[] args) {
        TypeSafeEventSource tses = new TypeSafeEventSource();
        tses.addListener(new StringEventObjectHandler());
        tses.addListener(new DoubleEventObjectHandler());
        
        Object[] obj = new Object[]{"abc",0.3541d,"asdkjl",213d,9013,"iju",90138,"iojbn"};
        for(Object o:obj) {
            tses.notifyListeners(o);
        }
    }
    
}
