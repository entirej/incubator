package org.entirej.applicationframework.rwt.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.entirej.applicationframework.rwt.renderers.form.EJRWTFormRenderer;
import org.entirej.framework.core.internal.EJInternalForm;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public class EJRWTFormPopupPage extends AbstractPage
{

    private static final long serialVersionUID = -4020410894639262478L;
    public static final String ID          = EJRWTFormPopupPage.class.getName();
    public static final String FORM_ID_KEY = "FPIK";

    @Override
    public void createContent(Composite parent, PageData data)
    {
        EJInternalForm form = data.get(FORM_ID_KEY, EJInternalForm.class);
        final  int height = form.getProperties().getFormHeight();
        final int width = form.getProperties().getFormWidth();
        EJRWTFormRenderer renderer = ((EJRWTFormRenderer) form.getRenderer());
        
       
        parent.setLayout(new FillLayout());
        final ScrolledComposite scrollComposite = new ScrolledComposite(parent,
                SWT.V_SCROLL | SWT.H_SCROLL);
        renderer.createControl(scrollComposite);
        scrollComposite.setContent(renderer.getGuiComponent());
        scrollComposite.setExpandHorizontal(true);
        scrollComposite.setExpandVertical(true);
        scrollComposite.setMinSize(width, height);
    }

}
