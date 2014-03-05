package org.entirej;

import org.entirej.applicationframework.rwt.application.launcher.EJRWTApplicationLauncher;
import org.entirej.ejinvoice.forms.constants.F_LAUNCH_PAGE;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.framework.core.EJFrameworkHelper;

public class ApplicationLauncher extends EJRWTApplicationLauncher {

	@Override
	public void postApplicationBuild(EJFrameworkHelper frameworkHelper) 
	{
	    frameworkHelper.openForm(F_LOGIN.ID, null, false);
	}
}
