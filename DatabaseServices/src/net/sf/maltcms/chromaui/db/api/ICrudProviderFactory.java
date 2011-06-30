/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.chromaui.db.api;

import java.net.URL;

/**
 *
 * @author hoffmann
 */
public interface ICrudProviderFactory {
    ICrudProvider getCrudProvider(URL databaseLocation, ICredentials ic, ClassLoader cl);
}
