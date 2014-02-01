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
package org.entirej.applicationframework.tmt.application;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.entirej.applicationframework.tmt.application.interfaces.EJTMTFormChosenEvent;
import org.entirej.applicationframework.tmt.application.interfaces.EJTMTFormChosenListener;
import org.entirej.applicationframework.tmt.application.interfaces.EJTMTFormClosedListener;
import org.entirej.applicationframework.tmt.application.interfaces.EJTMTFormContainer;
import org.entirej.applicationframework.tmt.application.interfaces.EJTMTFormOpenedListener;
import org.entirej.applicationframework.tmt.application.interfaces.EJTMTFormSelectedListener;
import org.entirej.applicationframework.tmt.application.launcher.EJTMTContext;
import org.entirej.applicationframework.tmt.pages.EJTMTFormPage;
import org.entirej.framework.core.data.controllers.EJPopupFormController;
import org.entirej.framework.core.internal.EJInternalForm;
import org.entirej.framework.core.properties.EJCoreFormProperties;
import org.entirej.framework.core.renderers.interfaces.EJApplicationComponentRenderer;

import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.PageData;
import com.eclipsesource.tabris.ui.PageStyle;
import com.eclipsesource.tabris.ui.UI;
import com.eclipsesource.tabris.ui.UIConfiguration;

public class EJTMTApplicationContainer implements Serializable, EJTMTFormOpenedListener, EJTMTFormClosedListener, EJTMTFormSelectedListener,
        EJTMTFormChosenListener
{
    protected EJTMTFormContainer      _formContainer;
    protected EJTMTApplicationManager _applicationManager;

    /**
     * Returns the {@link EJTMTFormContainer} used within this application
     * 
     * @return This applications {@link EJTMTFormContainer}
     */
    public EJTMTFormContainer getFormContainer()
    {
        return _formContainer;
    }

    void buildApplication(EJTMTApplicationManager applicationManager)
    {
        _applicationManager = applicationManager;

        // build tabris page base form container
        final UIConfiguration configuration = EJTMTContext.getUiConfiguration();
        final UI ui = EJTMTContext.getTabrisUI();
        if (ui != null && configuration != null)
        {
            _formContainer = new EJTMTFormContainer()
            {

                @Override
                public EJInternalForm switchToForm(String key)
                {
                    // not supported
                    return null;
                }

                @Override
                public void removeFormSelectedListener(EJTMTFormSelectedListener selectionListener)
                {
                    // ignore

                }

                @Override
                public void popupFormClosed()
                {
                    PageData currentPageData = ui.getPageOperator().getCurrentPageData();
                    if (currentPageData != null && currentPageData.get("POPUP", Boolean.class))
                    {
                        Page currentPage = ui.getPageOperator().getCurrentPage();
                        closeForm(((EJTMTFormPage) currentPage).getForm());
                    }

                }

                @Override
                public void openPopupForm(EJPopupFormController popupController)
                {
                    EJInternalForm form = popupController.getPopupForm();

                    final EJCoreFormProperties formProp = form.getProperties();
                    String pageID = toFormPageIDe(formProp.getName());
                    if (configuration.getPageConfiguration(pageID) == null)
                    {
                        PageConfiguration pageConfiguration = new PageConfiguration(pageID, EJTMTFormPage.class).setTitle(formProp.getTitle());
                        pageConfiguration.setStyle(PageStyle.FULLSCREEN);
                        configuration.addPageConfiguration(pageConfiguration);

                    }

                    PageData pageData = EJTMTFormPage.createPageData(form);
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
                    return currentPage instanceof EJTMTFormPage ? ((EJTMTFormPage) currentPage).getForm() : null;
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
                    if (pageId.equals(ui.getPageOperator().getCurrentPageId()))
                    {
                        ui.getPageOperator().closeCurrentPage();
                    }
                    // switch to page page and close;
                    PageConfiguration pageConfiguration = configuration.getPageConfiguration(pageId);
                    if (pageConfiguration != null)
                    {
                        configuration.removePageConfiguration(pageId);
                    }
                }

                @Override
                public void addFormSelectedListener(EJTMTFormSelectedListener selectionListener)
                {
                    // ignore

                }

                @Override
                public EJInternalForm addForm(EJInternalForm form)
                {
                    final EJCoreFormProperties formProp = form.getProperties();
                    String pageID = toFormPageIDe(formProp.getName());
                    if (configuration.getPageConfiguration(pageID) == null)
                    {
                        PageConfiguration pageConfiguration = new PageConfiguration(pageID, EJTMTFormPage.class).setTitle(formProp.getTitle());
                        pageConfiguration.setStyle(PageStyle.DEFAULT);
                        configuration.addPageConfiguration(pageConfiguration);

                    }

                    PageData pageData = EJTMTFormPage.createPageData(form);
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
     * Opens a new form and adds it to the applications chosen form container
     * <p>
     * If the form passed is <code>null</code> or not
     * {@link EJTMTFormContainer} has been implemented then this method will
     * do nothing
     * 
     * @param form
     *            The form to be opened and added to the
     *            {@link EJForm}
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
    public void formChosen(EJTMTFormChosenEvent event)
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
        EJTMTFormContainer formContainer = getFormContainer();
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
