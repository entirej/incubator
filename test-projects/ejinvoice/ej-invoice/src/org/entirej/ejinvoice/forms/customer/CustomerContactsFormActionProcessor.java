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

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER_CONTACTS;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJScreenType;

/**
 * This class is used to perform all business functionalities including data
 * validations related to CUSTOMER, CUSTOMER_CONTACT & CUSTOMER_PROJECTS block
 * service.
 */
public class CustomerContactsFormActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_CUSTOMER_CONTACTS.AC_QUERY_CONTACTS.equals(command))
        {
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).executeQuery();
        }
        else if (F_CUSTOMER_CONTACTS.AC_ADD_NEW_CONTACT.equals(command))
        {
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID).getFocusedRecord();
            
            Integer customerId = (Integer)form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_TOOLBAR.ID).getFocusedRecord().getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_TOOLBAR.I_CUSTOMER_ID);
            if (customerId != null)
            {
                insertRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_ID, customerId);
            }

            insertRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_INSERT_PAGE_TITLE, "Create a new Customer Contact");
            form.showStackedCanvasPage(F_CUSTOMER_CONTACTS.C_MAIN_STACK, F_CUSTOMER_CONTACTS.C_MAIN_STACK_PAGES.INSERT);

        }
        else if (F_CUSTOMER_CONTACTS.AC_DELETE_CUSTOMER_CONTACT.equals(command))
        {
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact?");
        }
        else if (F_CUSTOMER_CONTACTS.AC_EDIT_CONTACT.equals(command))
        {
                form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID).clear(true);
                EJRecord editRecord = form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID).getFocusedRecord();

                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_UPDATE_PAGE_TITLE, (record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_FIRST_NAME) + " " + record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_FIRST_NAME))+" - Edit");

                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_CONTACT_TYPES_ID));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_VALUE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_CONTACT_TYPE));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_EMAIL, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_EMAIL));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_FIRST_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_FIRST_NAME));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_LAST_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_LAST_NAME));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_MOBILE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_MOBILE));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_PHONE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_PHONE));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_SALUTATIONS_ID));
                editRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID_VALUE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_SALUTATION));

                form.showStackedCanvasPage(F_CUSTOMER_CONTACTS.C_MAIN_STACK, F_CUSTOMER_CONTACTS.C_MAIN_STACK_PAGES.EDIT);
        }
        else if (F_CUSTOMER_CONTACTS.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_ID);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_EMAIL);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_LAST_NAME);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID);
            
            Integer contactTypeId = (Integer) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_ID);
            String email = (String) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_EMAIL);
            String lastName = (String) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_LAST_NAME);
            Integer salutationId = (Integer) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID);

            boolean error = false;
            if (contactTypeId == null)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_ID, "Please choose a contact type");
            }
            if (email == null || email.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_EMAIL, "Please enter a valid email address");
            }
            if (lastName == null || lastName.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_LAST_NAME, "Please enter a last name");
            }
            if (salutationId == null)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_MOBILE, "Please choose a salutation");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }
            EJRecord baseRecord = form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).getFocusedRecord();

            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_CONTACT_TYPES_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_ID));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_EMAIL, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_EMAIL));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_FIRST_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_FIRST_NAME));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_LAST_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_LAST_NAME));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_MOBILE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_MOBILE));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_PHONE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_PHONE));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_SALUTATIONS_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID));

            CustomerContact customerContact = (CustomerContact)baseRecord.getBlockServicePojo();
           
            customerContact.setContactType((String)record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_VALUE));
            customerContact.setSalutation((String)record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID_VALUE));
            baseRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_DISPLAY_TEXT, customerContact.getDisplayText());

            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).updateRecord(baseRecord);
            form.saveChanges();

            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID).clear(true);

            form.showStackedCanvasPage(F_CUSTOMER_CONTACTS.C_MAIN_STACK, F_CUSTOMER_CONTACTS.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CUSTOMER_CONTACTS.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_ID);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_EMAIL);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_LAST_NAME);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_SALUTATIONS_ID);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CONTACT_TYPES_ID);
            
            Integer customerId = (Integer) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_ID);
            Integer contactTypeId = (Integer) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CONTACT_TYPES_ID);
            Integer salutationsId = (Integer) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_SALUTATIONS_ID);
            String contactLastName = (String) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_LAST_NAME);
            String email = (String) record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_EMAIL);

            boolean error = false;

            if (customerId == null)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_ID,  "Please choose a customer");
            }
            if (contactTypeId == null)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CONTACT_TYPES_ID,  "Please choose a type for the default contact");
            }
            if (salutationsId == null)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_SALUTATIONS_ID, "Please enter a salutation for the default contact");
            }
            if (contactLastName == null)
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_LAST_NAME,  "Please enter a last name for the default contact");
            }
            if (!EmailValidator.getInstance().isValid(email))
            {
                error = true;
                setError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_EMAIL,  "The email address you have entered is not a valid email address");
            }

            if (error)
            {
                throw new EJApplicationException();
            }
            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_ID, idSeqNextval);
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_COMPANY_ID, companyId);
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_CONTACT_TYPES_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CONTACT_TYPES_ID));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_CUSTOMER_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_ID));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_CUSTOMER_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_NAME));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_EMAIL,      email);
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_FIRST_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_FIRST_NAME));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_LAST_NAME, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_LAST_NAME));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_MOBILE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_MOBILE));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_PHONE, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_PHONE));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_SALUTATIONS_ID, record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_SALUTATIONS_ID));
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_COMPANY_ID, companyId);

            CustomerContact customerContact = (CustomerContact)newRecord.getBlockServicePojo();
            customerContact.setCustomerName((String)record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_NAME));
            customerContact.setContactType((String)record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CONTACT_TYPES_VALUE));
            customerContact.setSalutation((String)record.getValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_SALUTATIONS_ID_VALUE));
            
            newRecord.setValue(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.I_DISPLAY_TEXT, customerContact.getDisplayText());
            
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_CUSTOMER_CONTACTS.C_MAIN_STACK, F_CUSTOMER_CONTACTS.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CUSTOMER_CONTACTS.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_CONTACT_TYPES_ID);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_EMAIL);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_LAST_NAME);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.I_SALUTATIONS_ID);
            
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_CUSTOMER_CONTACTS.C_MAIN_STACK, F_CUSTOMER_CONTACTS.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CUSTOMER_CONTACTS.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CUSTOMER_ID);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_EMAIL);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_LAST_NAME);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_SALUTATIONS_ID);
            clearError(form, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.I_CONTACT_TYPES_ID);
            
            form.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_CUSTOMER_CONTACTS.C_MAIN_STACK, F_CUSTOMER_CONTACTS.C_MAIN_STACK_PAGES.MAIN);
        }
        
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        form.saveChanges();
    }
}
