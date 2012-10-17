package de.unibielefeld.gi.kotte.laborprogramm.utils;

import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;

/**
 * A java.lang.Runnable that is also a org.openide.util.Cancellable and handles
 * it's own org.netbeans.api.progress.ProgressHandle, starting and finishing it.
 *
 * @author kotte
 */
public abstract class CancellableRunnable implements Runnable, Cancellable {

    public ProgressHandle handle;

    public void setHandle(ProgressHandle handle) {
        this.handle = handle;
    }

    abstract void body();

    @Override
    public void run() {
        this.handle.start();
        try {
            body();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            this.handle.finish();
        }
    }

    @Override
    public boolean cancel() {
        this.handle.finish();
        return true;
    }
}
