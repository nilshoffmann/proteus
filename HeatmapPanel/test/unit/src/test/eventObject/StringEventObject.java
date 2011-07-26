/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.eventObject;

/**
 *
 * @author nilshoffmann
 */
public class StringEventObject implements IEventObject<String>{

    private final String cargo;
    private final long when;
    
    public StringEventObject(String s) {
        cargo = s;
        when = System.nanoTime();
    }
   
    @Override
    public long getWhen() {
        return when;
    }

    @Override
    public int compareTo(IEventObject t) {
        return Long.valueOf(when).compareTo(Long.valueOf(t.getWhen()));
    }

    @Override
    public String getCargo() {
        return this.cargo;
    }

    @Override
    public Class<? extends String> getCargoType() {
        return this.cargo.getClass();
    }

}
