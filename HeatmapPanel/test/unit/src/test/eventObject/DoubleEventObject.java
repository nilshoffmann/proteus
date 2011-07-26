/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.eventObject;

/**
 *
 * @author nilshoffmann
 */
public class DoubleEventObject implements IEventObject<Double> {

    private final Double cargo;
    private final long when;
    
    public DoubleEventObject(Double s) {
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
    public Double getCargo() {
        return this.cargo;
    }

    @Override
    public Class<? extends Double> getCargoType() {
        return this.cargo.getClass();
    }
    
    public static DoubleEventObject create(Double d) {
        if(d instanceof Double) {
            return new DoubleEventObject(d);
        }
        return null;
    }

}
