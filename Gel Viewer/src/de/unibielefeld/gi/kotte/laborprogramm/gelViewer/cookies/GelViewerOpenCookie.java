/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.GelViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IGelOpenCookie.class)
public class GelViewerOpenCookie implements IGelOpenCookie {

    @Override
    public void open() {
        IGel gel = Utilities.actionsGlobalContext().lookup(IGel.class);
        if(gel != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponent(gel,GelViewerTopComponent.class);
        }
    }

}
