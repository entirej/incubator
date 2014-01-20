package org.entirej.applicationframework.rwt.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.entirej.applicationframework.rwt.application.EJRWTApplicationManager;
import org.entirej.applicationframework.rwt.application.launcher.EJRWTContext;
import org.entirej.applicationframework.rwt.layout.EJRWTEntireJGridPane;
import org.entirej.applicationframework.rwt.renderers.form.EJRWTFormRenderer;
import org.entirej.framework.core.internal.EJInternalForm;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public abstract class EJRWTFormComponentPage extends AbstractPage
{

    private static final long serialVersionUID = -4020410894639262478L;
   
    private  String formId = null;
    public EJRWTFormComponentPage(String menuId)
    {
       this.formId = menuId;
    }
    
    
    @Override
    public void createContent(Composite parent, PageData data)
    {
        EJRWTApplicationManager manager = EJRWTContext.getEJRWTApplicationManager();
        if (formId != null)
        {
            try
            {
                
                
                EJInternalForm form = manager.getFrameworkManager().createInternalForm(formId, null);
                if (form != null)
                {
                    Composite composite = new Composite(parent, SWT.BORDER);
                    FillLayout fillLayout = new FillLayout();
                    fillLayout.marginHeight =5;
                    fillLayout.marginWidth =5;
                    composite.setLayout(fillLayout);
                    EJRWTFormRenderer renderer = ((EJRWTFormRenderer) form.getRenderer());
                    renderer.createControl(composite);
                    EJRWTEntireJGridPane gridPane = renderer.getGuiComponent();
                    gridPane.cleanLayout();
                    return;
                }
            }
            catch (Exception e)
            {

                manager.getApplicationMessenger().handleException(e, true);
            }
        }

        Label label = new Label(parent, SWT.NONE);
        label.setText("Form did not found ID#:" + (formId != null ? formId : "<null>"));
    }

}
