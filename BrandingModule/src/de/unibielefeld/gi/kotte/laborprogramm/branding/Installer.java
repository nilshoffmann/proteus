package de.unibielefeld.gi.kotte.laborprogramm.branding;

import java.io.File;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

	@Override
	public void restored() {
		File projectsDir = new File(new File(System.getProperty("user.home")), "ProteusProjects");
        if (!projectsDir.exists()) {
            projectsDir.mkdirs();
        }
        System.setProperty("netbeans.projects.dir", projectsDir.getAbsolutePath());
	}
}
