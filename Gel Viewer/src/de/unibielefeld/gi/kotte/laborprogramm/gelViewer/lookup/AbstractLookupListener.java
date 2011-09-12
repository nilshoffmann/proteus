/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup;

import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author nilshoffmann
 */
public abstract class AbstractLookupListener<T> implements LookupListener {

    private Result<? extends T> result;

    private Class<? extends T> typeToListenFor;

    private Lookup contentProviderLookup;

    public AbstractLookupListener(Class<? extends T> typeToListenFor) {
        this(typeToListenFor,Utilities.actionsGlobalContext());
    }

    public AbstractLookupListener(Class<? extends T> typeToListenFor, Lookup contentProviderLookup) {
        this.typeToListenFor = typeToListenFor;
        this.contentProviderLookup = contentProviderLookup;
    }

    public void register(Lookup targetLookup) {
        result = targetLookup.lookupResult(typeToListenFor);
        result.addLookupListener(this);
    }

    public void deregister() {
        result.removeLookupListener(this);
        result = null;
    }

    public Result<? extends T> getResult() {
        return result;
    }

    public Lookup getContentProviderLookup() {
        return contentProviderLookup;
    }

    public Class<? extends T> getTypeToListenFor() {
        return typeToListenFor;
    }

}
