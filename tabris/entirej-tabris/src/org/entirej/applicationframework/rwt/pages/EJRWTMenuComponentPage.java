package org.entirej.applicationframework.rwt.pages;

import org.eclipse.swt.widgets.Composite;
import org.entirej.applicationframework.rwt.application.components.menu.EJRWTDefaultMenuBuilder;
import org.entirej.applicationframework.rwt.application.launcher.EJRWTContext;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public abstract class EJRWTMenuComponentPage extends AbstractPage
{

    private static final long serialVersionUID = -4020410894639262478L;
   
    private  String menuId = null;
    public EJRWTMenuComponentPage(String menuId)
    {
       this.menuId = menuId;
    }
    
    
    @Override
    public void createContent(Composite parent, PageData data)
    {
        
        EJRWTDefaultMenuBuilder _menuBuilder = new EJRWTDefaultMenuBuilder(EJRWTContext.getEJRWTApplicationManager(), parent);
         _menuBuilder.createTreeComponent(menuId);
    }

}
