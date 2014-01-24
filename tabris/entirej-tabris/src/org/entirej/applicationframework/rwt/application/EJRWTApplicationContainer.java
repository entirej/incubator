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
package org.entirej.applicationframework.rwt.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.entirej.applicationframework.rwt.application.form.containers.EJRWTAbstractDialog;
import org.entirej.applicationframework.rwt.application.form.containers.EJRWTFormPopUp;
import org.entirej.applicationframework.rwt.application.form.containers.EJRWTSingleFormContainer;
import org.entirej.applicationframework.rwt.application.interfaces.EJRWTFormChosenEvent;
import org.entirej.applicationframework.rwt.application.interfaces.EJRWTFormChosenListener;
import org.entirej.applicationframework.rwt.application.interfaces.EJRWTFormClosedListener;
import org.entirej.applicationframework.rwt.application.interfaces.EJRWTFormContainer;
import org.entirej.applicationframework.rwt.application.interfaces.EJRWTFormOpenedListener;
import org.entirej.applicationframework.rwt.application.interfaces.EJRWTFormSelectedListener;
import org.entirej.applicationframework.rwt.application.launcher.EJRWTContext;
import org.entirej.applicationframework.rwt.pages.EJRWTFormPage;
import org.entirej.applicationframework.rwt.renderers.form.EJRWTFormRenderer;
import org.entirej.framework.core.data.controllers.EJPopupFormController;
import org.entirej.framework.core.internal.EJInternalForm;
import org.entirej.framework.core.properties.EJCoreFormProperties;
import org.entirej.framework.core.renderers.interfaces.EJApplicationComponentRenderer;

import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.PageData;
import com.eclipsesource.tabris.ui.PageStyle;
import com.eclipsesource.tabris.ui.TabrisUI;
import com.eclipsesource.tabris.ui.UI;
import com.eclipsesource.tabris.ui.UIConfiguration;

public class EJRWTApplicationContainer implements Serializable, EJRWTFormOpenedListener, EJRWTFormClosedListener, EJRWTFormSelectedListener,
        EJRWTFormChosenListener
{

    private static final long                 serialVersionUID      = 1L;


    protected EJRWTFormContainer              _formContainer;
    protected List<EJRWTSingleFormContainer>  _singleFormContainers = new ArrayList<EJRWTSingleFormContainer>();
    protected EJRWTApplicationManager         _applicationManager;

  

    /**
     * Returns the {@link EJSwingFormContainer} used within this application
     * 
     * @return This applications {@link EJSwingFormContainer}
     */
    public EJRWTFormContainer getFormContainer()
    {
        return _formContainer;
    }

   

    

    void buildApplication(EJRWTApplicationManager applicationManager)
    {
        _applicationManager = applicationManager;

       //build tabris page base form container 
        final UIConfiguration configuration = EJRWTContext.getUiConfiguration();
        final UI ui = EJRWTContext.getTabrisUI();
        if(ui != null && configuration !=null)
        {
            _formContainer = new EJRWTFormContainer()
            {
                
                @Override
                public EJInternalForm switchToForm(String key)
                {
                    //not supported
                    return null;
                }
                
                @Override
                public void removeFormSelectedListener(EJRWTFormSelectedListener selectionListener)
                {
                    //ignore
                    
                }
                
                @Override
                public void popupFormClosed()
                {
                    PageData currentPageData = ui.getPageOperator().getCurrentPageData();
                    if(currentPageData!=null && currentPageData.get("POPUP", Boolean.class))
                    {
                        Page currentPage = ui.getPageOperator().getCurrentPage();
                        closeForm(((EJRWTFormPage)currentPage).getForm());
                    }
                    
                }
                
                @Override
                public void openPopupForm(EJPopupFormController popupController)
                {
                    EJInternalForm form = popupController.getPopupForm();
                    
                    final EJCoreFormProperties formProp = form.getProperties();
                    String pageID = toFormPageIDe(formProp.getName());
                    if(configuration.getPageConfiguration(pageID)==null)
                    {
                        PageConfiguration pageConfiguration = new PageConfiguration( pageID, EJRWTFormPage.class )
                        .setTitle( formProp.getTitle() );
                        pageConfiguration.setStyle(PageStyle.FULLSCREEN);
                        configuration.addPageConfiguration(pageConfiguration);
                        
                    }
                    
                    PageData pageData = EJRWTFormPage.createPageData(form);
                    pageData.set("POPUP", Boolean.TRUE);
                    ui.getPageOperator().openPage(pageID, pageData);
                    
                    
                }
                
                @Override
                public Collection<EJInternalForm> getAllForms()
                {
                    return Collections.emptyList();
                }
                
                @Override
                public EJInternalForm getActiveForm()
                {
                    Page currentPage = ui.getPageOperator().getCurrentPage();
                    return currentPage instanceof EJRWTFormPage ? ((EJRWTFormPage)currentPage).getForm() : null;
                }
                
                @Override
                public boolean containsForm(String formName)
                {
                    return false;
                }
                
                @Override
                public void closeForm(EJInternalForm form)
                {

                    String pageId = toFormPageIDe(form.getProperties().getName());
                    if(pageId.equals( ui.getPageOperator().getCurrentPageId()))
                    {
                        ui.getPageOperator().closeCurrentPage();
                    }
                    //switch to page page and close;
                    PageConfiguration pageConfiguration = configuration.getPageConfiguration(pageId);
                    if(pageConfiguration!=null)
                    {
                        configuration.removePageConfiguration(pageId);
                    }
                }
                
                @Override
                public void addFormSelectedListener(EJRWTFormSelectedListener selectionListener)
                {
                    //ignore
                    
                }
                
                @Override
                public EJInternalForm addForm(EJInternalForm form)
                {
                    final EJCoreFormProperties formProp = form.getProperties();
                    String pageID = toFormPageIDe(formProp.getName());
                    if(configuration.getPageConfiguration(pageID)==null)
                    {
                        PageConfiguration pageConfiguration = new PageConfiguration( pageID, EJRWTFormPage.class )
                        .setTitle( formProp.getTitle() );
                        pageConfiguration.setStyle(PageStyle.DEFAULT);
                        configuration.addPageConfiguration(pageConfiguration);
                        
                    }
                    
                    PageData pageData = EJRWTFormPage.createPageData(form);
                    pageData.set("POPUP", Boolean.FALSE);
                    ui.getPageOperator().openPage(pageID, pageData);
                    return form;
                }

                private String toFormPageIDe(String name)
                {
                    return String.format("EJTF_%s", name);
                }
            };
        }
        //fallback dialog base options
        if (_formContainer == null)
        {
            _formContainer = new EJRWTFormContainer()
            {
                EJRWTAbstractDialog _popupDialog;
                EJRWTFormPopUp      _formPopup;

                @Override
                public EJInternalForm switchToForm(String key)
                {
                    // ignore
                    return null;
                }

                @Override
                public void removeFormSelectedListener(EJRWTFormSelectedListener selectionListener)
                {
                    // ignore
                }

                @Override
                public void popupFormClosed()
                {
                    if (_formPopup != null)
                    {
                        _formPopup.close();
                        _formPopup = null;
                    }
                }

                @Override
                public void openPopupForm(EJPopupFormController popupController)
                {
                    _formPopup = new EJRWTFormPopUp(_applicationManager.getShell(), popupController);
                    _formPopup.showForm();

                }

                @Override
                public Collection<EJInternalForm> getAllForms()
                {
                    // ignore
                    return Collections.emptyList();
                }

                @Override
                public EJInternalForm getActiveForm()
                {
                    // ignore
                    return null;
                }

                @Override
                public boolean containsForm(String formName)
                {
                    // ignore
                    return false;
                }

                @Override
                public void closeForm(EJInternalForm form)
                {
                    if (_popupDialog != null)
                    {
                        _popupDialog.close();
                        _popupDialog = null;
                    }

                }

                @Override
                public void addFormSelectedListener(EJRWTFormSelectedListener selectionListener)
                {
                    // ignore

                }

                @Override
                public EJInternalForm addForm(final EJInternalForm form)
                {
                    

                    final EJRWTFormRenderer formRenderer = (EJRWTFormRenderer) form.getRenderer();
                    _popupDialog = new EJRWTAbstractDialog(_applicationManager.getShell())
                    {
                        private static final long serialVersionUID = -4685316941898120169L;

                        @Override
                        public void createBody(Composite parent)
                        {
                            parent.setLayout(new FillLayout());
                            final ScrolledComposite scrollComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
                            formRenderer.createControl(scrollComposite);
                            scrollComposite.setContent(formRenderer.getGuiComponent());
                            scrollComposite.setExpandHorizontal(true);
                            scrollComposite.setExpandVertical(true);
                            scrollComposite.setMinSize(form.getProperties().getFormWidth(), form.getProperties().getFormHeight());

                            formRenderer.gainInitialFocus();
                        }

                        @Override
                        public int open()
                        {
                            return super.open();
                        }

                        private void addExtraButton(Composite parent, String label, int id)
                        {
                            if (label == null || label.length() == 0)
                            {
                                return;
                            }
                            createButton(parent, id, label, false);

                        }

                        @Override
                        public boolean close()
                        {
                            return super.close();
                        }

                    };
                    _popupDialog.create();
                    final EJCoreFormProperties coreFormProperties = form.getProperties();
                    _popupDialog.getShell().setData("POPUP - " + coreFormProperties.getName());
                    _popupDialog.getShell().setText(coreFormProperties.getTitle() == null ? coreFormProperties.getName() : coreFormProperties.getTitle());
                    // add dialog border offsets
                    _popupDialog.getShell().setMaximized(true);
                    _popupDialog.centreLocation();
                    _popupDialog.open();
                    return form;
                }
            };
        }

    }

    /**
     * Returns the currently active form
     * 
     * @return The currently active form or <code>null</code> if there is
     *         currently no active form
     */
    public EJInternalForm getActiveForm()
    {
        return _formContainer != null ? _formContainer.getActiveForm() : null;
    }

    /**
     * Returns the amount of forms currently opened and stored within the form
     * container
     * 
     * @return The amount of forms currently opened
     */
    public int getOpenFormCount()
    {
        return _formContainer != null ? _formContainer.getAllForms().size() : 0;
    }

    /**
     * Instructs the form container to close the given form
     * 
     * @param form
     *            The form to close
     */
    public void remove(EJInternalForm form)
    {
        if (_formContainer != null)
        {
            _formContainer.closeForm(form);
        }

        // Inform the listeners that the form has been closed
        fireFormClosed(form);
    }

    /**
     * Opens a new form and adds it to the FormContainer
     * <p>
     * If the form passed is <code>null</code> or not
     * {@link EJSwingFormContainer} has been implemented then this method will
     * do nothing
     * 
     * @param form
     *            The form to be opened and added to the
     *            {@link EJSwingFormContainer}
     */
    public void add(EJInternalForm form)
    {
        if (form == null)
        {
            return;
        }

        if (_formContainer != null)
        {
            EJInternalForm addForm = _formContainer.addForm(form);
            // Inform the listeners that the form was opened
            fireFormOpened(addForm);
        }
    }

    public boolean isFormOpened(String formName)
    {

        return getForm(formName) != null;
    }



    

    @Override
    public void formChosen(EJRWTFormChosenEvent event)
    {
        EJInternalForm form = _applicationManager.getFrameworkManager().createInternalForm(event.getChosenFormName(), null);
        if (form != null)
        {
            add(form);
        }
    }

    @Override
    public void fireFormClosed(EJInternalForm closedForm)
    {
       
    }

    @Override
    public void fireFormOpened(EJInternalForm openedForm)
    {
        
    }

    @Override
    public void fireFormSelected(EJInternalForm selectedForm)
    {
        
    }

    public EJInternalForm getForm(String formName)
    {

        for (EJRWTSingleFormContainer singleFormContainer : _singleFormContainers)
        {
            if (singleFormContainer.getForm() != null && formName.equals(singleFormContainer.getForm().getProperties().getName()))
            {
                return singleFormContainer.getForm();
            }
        }

        for (EJInternalForm form : getFormContainer().getAllForms())
        {
            if (formName.equals(form.getProperties().getName()))
            {
                return form;
            }
        }

        return null;
    }

    public EJInternalForm switchToForm(String key)
    {
        EJRWTFormContainer formContainer = getFormContainer();
        if (formContainer != null)
        {
            EJInternalForm switchToForm = formContainer.switchToForm(key);
            if (switchToForm != null)
            {
                if (formContainer instanceof EJApplicationComponentRenderer)
                {
                    switchTabs((Control) ((EJApplicationComponentRenderer) formContainer).getGuiComponent());
                }

                return switchToForm;
            }
        }
        for (EJRWTSingleFormContainer container : _singleFormContainers)
        {
            if (container.getForm() != null && key.equalsIgnoreCase(container.getForm().getProperties().getName()))
            {
                if (container instanceof EJApplicationComponentRenderer)
                {
                    switchTabs((Control) ((EJApplicationComponentRenderer) container).getGuiComponent());
                }
                return container.getForm();
            }
        }
        return null;
    }

    private void switchTabs(Control control)
    {
        if (control == null || control.isDisposed())
        {
            return;
        }

        Control parent = control.getParent();
        while (parent != null && !parent.isDisposed())
        {
            if (parent.getData("TAB_ITEM") != null)
            {
                CTabItem data = (CTabItem) parent.getData("TAB_ITEM");
                data.getParent().setSelection(data);
                parent = null;
                switchTabs(data.getParent());
            }
            else
            {
                parent = parent.getParent();
            }
        }
    }
}
