/*******************************************************************************
 * Copyright 2013 Mojave Innovations GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Mojave Innovations GmbH - initial API and implementation
 ******************************************************************************/
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
