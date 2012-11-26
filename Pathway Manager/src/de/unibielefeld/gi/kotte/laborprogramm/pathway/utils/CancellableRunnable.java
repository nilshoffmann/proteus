package de.unibielefeld.gi.kotte.laborprogramm.pathway.utils;

import java.util.HashSet;
import javax.swing.SwingUtilities;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;

/**
 * A java.lang.Runnable that is also a org.openide.util.Cancellable and handles
 * it's own org.netbeans.api.progress.ProgressHandle, starting and finishing it.
 *
 * Allows registration of {@link ResultListener} instances for the same generic
 * type used at instantiation time of this class. To notify listeners of the
 * result, call
 * <code>notifyListeners(R result)</code> from within the
 * <code>body()</code> method. All registered listeners will be notified.
 *
 * @author kotte
 * @author Nils Hoffmann
 */
public abstract class CancellableRunnable<R> implements Runnable, Cancellable {

    private HashSet<ResultListener<R>> listeners = new HashSet<ResultListener<R>>();
    private boolean updateOnEventDispatchThread = false;
    public ProgressHandle handle;

    public CancellableRunnable() {
    }

    /**
     * @param updateOnEventDispatchThread if true, each listener will be
     * notified via their own Runnable using
     * javax.swing.SwingUtilities.invokeLater()
     */
    public CancellableRunnable(boolean updateOnEventDispatchThread) {
        this.updateOnEventDispatchThread = updateOnEventDispatchThread;
    }

    public void setHandle(ProgressHandle handle) {
        this.handle = handle;
    }

    public abstract void body();

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

    public void notifyListeners(final R result) {
        if (updateOnEventDispatchThread) {
            for (final ResultListener<R> listener : this.listeners) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listener.listen(result);
                    }
                });
            }
        } else {
            for (final ResultListener<R> listener : this.listeners) {
                listener.listen(result);
            }
        }
    }

    public void addResultListener(ResultListener<R> listener) {
        this.listeners.add(listener);
    }

    public void removeResultListener(ResultListener<R> listener) {
        this.listeners.remove(listener);
    }
}
