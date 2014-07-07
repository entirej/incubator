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
/**
 * 
 */
package org.entirej.ejinvoice.forms.customer;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.enumerations.EJScreenType;

/**
 * This class is used to perform all business functionalities including data
 * validations related to CUSTOMER, CUSTOMER_CONTACT & CUSTOMER_PROJECTS block
 * service.
 */
public class CustomerFormActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        if (form.getParameterList().getParameter(F_CUSTOMER.P_CST_ID) != null)
        {
            form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).executeQuery();
        }

    }
    
    @Override
  public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
  {
        if (F_CUSTOMER.AC_OPEN_EMAIL.equals(command))
        {
            String email = (String)record.getValue(F_CUSTOMER.B_CUSTOMER_CONTACTS.I_EMAIL);
            if (email != null)
            {
                UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
                launcher.openURL( "mailto:"+email);
            }
        }
  }
    
//
//    @Override
//    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
//    {
//
//        if (F_CUSTOMER.AC_TOOLBAR_HOME.equals(command))
//        {
//            form.openForm(F_TIME_ENTRY.ID);
//            return;
//        }
//        if (F_CUSTOMER.AC_TOOLBAR_NEW.equals(command))
//        {
//            form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterInsert(false);
//            return;
//        }
//        if (F_CUSTOMER.AC_TOOLBAR_EDIT.equals(command))
//        {
//            form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterUpdate();
//            return;
//        }
//        if (F_CUSTOMER.AC_TOOLBAR_DELETE.equals(command) && form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null)
//        {
//            // before deleting the selected record from database validate
//            // and check if the record to be deleted has any FK constraints
//            // usage with other table data and if so throw an exception and
//            // block physical delete
//            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord(), "CUSTOMER");
//
//            // If you are using codes for you texts, pass the code to entirej so
//            // that it can be
//            // translate by your application tanslator:
//            //
//            // String translatedText =
//            // form.translateMessageText("askToDeleteCustomer");
//            //
//            // form more information on EntireJ's translators read:
//            // http://http://docs.entirej.com/display/EJ1/EntireJ+Translator
//
//            form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).askToDeleteCurrentRecord("Are you sure you want to delete this customer?");
//            return;
//        }
//
//        else if (record.getBlockName() != null && ((record.getBlockName().equals(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID)) || record.getBlockName().equals(F_CUSTOMER.B_CUST_CONTACT_TOOLBAR.ID)))
//        {
//            if (F_CUSTOMER.AC_NEW_CONTACT.equals(command))
//            {
//                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).enterInsert(false);
//                return;
//            }
//            if (F_CUSTOMER.AC_EDIT_CONTACT.equals(command))
//            {
//                EJBlock contactBlock = form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID);
//                contactBlock.enterUpdate();
//                return;
//            }
//            if (F_CUSTOMER.AC_DELETE_CONTACT.equals(command) && form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).getFocusedRecord() != null)
//            {
//                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact?");
//                return;
//            }
//        }
//
//    }
//
//
//    @Override
//    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
//    {
//        if(F_CUSTOMER.B_CUSTOMER.ID.equals(record.getBlockName()))
//        {
//            validateToolbarState(form.getBlock(F_CUSTOMER.B_CUSTOMER_TOOL_BAR.ID), form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null);
//        }
//        else if(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(record.getBlockName()))
//        {
//            updateCustomerContactToolbar(form);
//        }
//    }

//    
//    @Override
//    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
//    {
//        super.postDelete(form, record);
//        form.saveChanges();
//        if(F_CUSTOMER.B_CUSTOMER.ID.equals(record.getBlockName()))
//        {
//            validateToolbarState(form.getBlock(F_CUSTOMER.B_CUSTOMER_TOOL_BAR.ID), form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null);
//        }
//        else if(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(record.getBlockName()))
//        {
//            updateCustomerContactToolbar(form);
//        }
//
//    }
//
//
//


// 
//
//    @Override
//    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
//    {
//        if(F_CUSTOMER.B_CUSTOMER.ID.equals(record.getBlockName()))
//        {
//            validateToolbarState(form.getBlock(F_CUSTOMER.B_CUSTOMER_TOOL_BAR.ID), form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null);
//        }
//        else if(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(record.getBlockName()))
//        {
//            updateCustomerContactToolbar(form);
//        }
//
//    }
//
//    @Override
//    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
//    {
//        if(F_CUSTOMER.B_CUSTOMER.ID.equals(block.getName()))
//        {
//            validateToolbarState(form.getBlock(F_CUSTOMER.B_CUSTOMER_TOOL_BAR.ID), form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null);
//        }
//        else if(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(block.getName()))
//        {
//            updateCustomerContactToolbar(form);
//        }
//    }

    /**
     * This method is used to validate the customer contact toolbar to
     * disable/enable delete button/link state depending on the selected contact
     * records contact type
     * 
     * @param form
     *            - customer contact form
     * @param record
     *            - customer contact record
     */
//    private void updateCustomerContactToolbar(EJForm form)
//    {
//
//        
//
//            if (form.getBlock(F_CUSTOMER.B_CUST_CONTACT_TOOLBAR.ID) != null)
//            {
//                EJBlock customerContactBlock = form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID);
//                final EJRecord contactRecord = customerContactBlock.getFocusedRecord();
//
//                if (contactRecord != null)
//                {
//                   
//                    
//                }
//                
//                EJBlock toolbarBlock = form.getBlock(F_CUSTOMER.B_CUST_CONTACT_TOOLBAR.ID);
//                toolbarBlock.getScreenItem(EJScreenType.MAIN, F_CUSTOMER.B_CUST_CONTACT_TOOLBAR.I_DELETE).setEditable(contactRecord != null);
//                toolbarBlock.getScreenItem(EJScreenType.MAIN, F_CUSTOMER.B_CUST_CONTACT_TOOLBAR.I_EDIT).setEditable(contactRecord != null);
//            }
//        
//
//    }

}
