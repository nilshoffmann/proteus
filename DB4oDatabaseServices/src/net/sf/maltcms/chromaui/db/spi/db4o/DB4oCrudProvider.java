/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.chromaui.db.spi.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.reflect.jdk.JdkReflector;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.io.File;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.maltcms.chromaui.db.api.ICredentials;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import net.sf.maltcms.chromaui.db.api.ICrudProviderFactory;
import net.sf.maltcms.chromaui.db.api.ICrudSession;
import net.sf.maltcms.chromaui.db.api.exceptions.AuthenticationException;

/**
 * ICrudProvider implementation for DB4o object database.
 * @author Nils.Hoffmann@cebitec.uni-bielefeld.de
 */
public final class DB4oCrudProvider implements ICrudProvider {

    private EmbeddedObjectContainer eoc;
    private final File projectDBLocation;
    private final ICredentials ic;
    private final ClassLoader domainClassLoader;
    private final ICrudProviderFactory factory;
    private HashSet<ICrudSession> sessionCache;

    /**
     * Throws IllegalArgumentException if either projectDBFile or ic are null.
     * @param projectDBFile
     * @param ic
     * @param classLoader
     * @throws IllegalArgumentException
     */
    public DB4oCrudProvider(File projectDBFile, ICredentials ic,
            ClassLoader domainClassLoader) throws IllegalArgumentException {
        this(projectDBFile, ic, domainClassLoader, null);
    }

    /**
     * Throws IllegalArgumentException if either projectDBFile or ic are null.
     * @param projectDBFile
     * @param ic
     * @param classLoader
     * @param factory
     * @throws IllegalArgumentException
     */
    public DB4oCrudProvider(File projectDBFile, ICredentials ic,
            ClassLoader domainClassLoader, ICrudProviderFactory factory) throws IllegalArgumentException {
        if (ic == null) {
            throw new IllegalArgumentException(
                    "Credentials Provider must not be null!");
        }
        if (projectDBFile == null) {
            throw new IllegalArgumentException(
                    "Project database file must not be null!");
        }
        if (projectDBFile.isDirectory()) {
            throw new IllegalArgumentException(
                    "Project database file is a directory!");
        }
        this.ic = ic;
        System.out.println("Using crud provider on database file: " + projectDBFile.
                getAbsolutePath());
        projectDBLocation = projectDBFile;
        System.out.println("Using class loader: " + domainClassLoader);
        this.domainClassLoader = domainClassLoader;
        this.factory = factory;
        this.sessionCache = new HashSet<ICrudSession>();
    }

    @Override
    public final void open() {
        authenticate();
        if (eoc == null) {
            System.out.println("Opening ObjectContainer at " + projectDBLocation.
                    getAbsolutePath());
            EmbeddedConfiguration ec = com.db4o.Db4oEmbedded.newConfiguration();
//            ec.common().activationDepth(10);
            ec.common().reflectWith(new JdkReflector(this.domainClassLoader));
            ec.common().add(new TransparentActivationSupport());
            ec.common().add(new TransparentPersistenceSupport());
            ec.common().objectClass(GeneralPath.class).storeTransientFields(true);
            ec.common().objectClass(Path2D.class).storeTransientFields(true);
            ec.common().objectClass(Path2D.Float.class).storeTransientFields(true);
            ec.common().objectClass(Path2D.Double.class).storeTransientFields(true);
            eoc = Db4oEmbedded.openFile(ec, projectDBLocation.getAbsolutePath());
//            sessionCache = new HashSet<ICrudSession>();
        }
    }

    @Override
    public final void close() {
        authenticate();
        for (ICrudSession ics : sessionCache) {
            try {
                ics.close();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING,
                        "Caught exception while trying to close crud session: {0}",
                        e);
            }
        }
        sessionCache = new HashSet<ICrudSession>();
        if (eoc != null) {
            eoc.close();
            eoc = null;
        }
        if (factory != null) {
            factory.remove(this);
        }
    }

    private void authenticate() throws AuthenticationException {
        if (!ic.authenticate()) {
            throw new AuthenticationException(
                    "Invalid credentials for user, check username and password!");
        }
    }

    @Override
    public final ICrudSession createSession() {
        open();
        ICrudSession ics = new DB4oCrudSession(ic, eoc);
        sessionCache.add(ics);
        return ics;
    }
}
