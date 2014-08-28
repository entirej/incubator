package org.entirej;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.graphics.ImageFactory;
import org.entirej.applicationframework.rwt.application.EJRWTImageRetriever;
import org.entirej.applicationframework.rwt.application.launcher.EJRWTApplicationLauncher;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.framework.core.EJFrameworkHelper;

public class ApplicationLauncher extends EJRWTApplicationLauncher
{

    @Override
    public void postApplicationBuild(EJFrameworkHelper frameworkHelper)
    {
        frameworkHelper.setApplicationLevelParameter(EJ_PROPERTIES.P_BUILT_BY, "<a style='color:#' href=\"http://entirej.com/\" target=\"_blank\">"+createEJImageTag()+"</a>");
        frameworkHelper.openForm(F_LOGIN.ID, null, false);
    }

//    protected String getBaseThemeCSSLocation()
//    {
//        return "resource/theme/default.css";
//    }

    
    private String createEJImageTag() {
        Image image = EJRWTImageRetriever.get("icons/ej_text.png");
        String imageUrl = ImageFactory.getImagePath(image);
        return "<img src='" + imageUrl + "' width='55' height='18' style='padding-right:1px;vertical-align:middle;'/>";
      }
    
    
    
    @Override
    protected String getThemeCSSLocation()
    {
        // return "resource/theme/default.css";

        return "org/entirej/ejinvoice/theme/ejinvoice.css";
    }
}
