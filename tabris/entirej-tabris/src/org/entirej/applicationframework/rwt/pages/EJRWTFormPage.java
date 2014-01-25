package org.entirej.applicationframework.rwt.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.entirej.applicationframework.rwt.renderers.form.EJRWTFormRenderer;
import org.entirej.framework.core.internal.EJInternalForm;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public class EJRWTFormPage extends AbstractPage
{


    private static final long serialVersionUID = 406668660828951594L;
    public static final String FORM_ID_KEY = "FPIK";
    
    private  EJInternalForm form;
    
    
    @Override
    public void createContent(Composite parent, PageData data)
    {
         form = data.get(FORM_ID_KEY, EJInternalForm.class);
        if(form!=null)
        {
            final  int height = form.getProperties().getFormHeight();
            final int width = form.getProperties().getFormWidth();
            EJRWTFormRenderer renderer = ((EJRWTFormRenderer) form.getRenderer());
            
            FillLayout fillLayout = new FillLayout();
            parent.setLayout(fillLayout);
            final ScrolledComposite scrollComposite = new ScrolledComposite(parent,
                    SWT.V_SCROLL | SWT.H_SCROLL);
            renderer.createControl(scrollComposite);
            scrollComposite.setContent(renderer.getGuiComponent());
            scrollComposite.setExpandHorizontal(true);
            scrollComposite.setExpandVertical(true);
            scrollComposite.setMinSize(width, height);
        }
    }
    
    
    public EJInternalForm getForm()
    {
        return form;
    }
    
    public static PageData createPageData(EJInternalForm form)
    {
        PageData data = new PageData();
        data.set(FORM_ID_KEY, form);
        return data;
    }
    
    

}
