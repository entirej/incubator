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
    public EJRWTFormComponentPage(String formid)
    {
       this.formId = formid;
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
