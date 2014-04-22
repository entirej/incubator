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

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;

/**
 * This class is used to perform all business functionalities including data
 * validations related to CUSTOMER, CUSTOMER_CONTACT  block
 * service.
 */
public class CustomerFormActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{

    public static final String QUESTION_ASK_CREATE_CUSTOMER         = "CREATE_CUSTOMER";
    public static final String QUESTION_ASK_CREATE_CUSTOMER_CONTACT = "CREATE_CUSTOMER_CONTACT";

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);

        EJBlock customerBlock = form.getBlock(F_CUSTOMER.B_CUSTOMER.ID);
        customerBlock.executeQuery();
        
    }
    

    
    @Override
    public void focusGained(EJForm form) throws EJActionProcessorException
    {
        if(form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getBlockRecords().isEmpty())
        {
            askTOCreateCustomer(form);
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(record.getBlockName()))
        {
            record.getItem(F_CUSTOMER.B_CUSTOMER_CONTACTS.I_FULL_NAME).setValue(record.getValue(F_CUSTOMER.B_CUSTOMER_CONTACTS.I_FIRST_NAME) + " " + record.getValue(F_CUSTOMER.B_CUSTOMER_CONTACTS.I_LAST_NAME));
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (form.getFocusedBlock() == null)
        {
            return;
        }

        String blockName = form.getFocusedBlock().getName();

        if (F_CUSTOMER.AC_SHOW_CONTACTS.equals(command))
        {
            form.showPopupCanvas(F_CUSTOMER.C_CUSTOMER_CONTACTS_POPUP);
        }
        if (F_CUSTOMER.AC_NEW.equals(command))
        {
            if (F_CUSTOMER.B_CUSTOMER.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterInsert(false);
            }
            else if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).enterInsert(false);
            }
            return;
        }
        if (F_CUSTOMER.AC_EDIT.equals(command))
        {
            if (F_CUSTOMER.B_CUSTOMER.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterUpdate();
            }
            else if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).enterUpdate();
            }
            return;
        }
        if (F_CUSTOMER.AC_DELETE.equals(command) && form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null)
        {
            if (F_CUSTOMER.B_CUSTOMER.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord(), "CUSTOMER");
                form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).askToDeleteCurrentRecord("Are you sure you want to delete this customer?");
            }
            else if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).getFocusedRecord(), "CUSTOMER_CONTACT");
                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact?");
            }

            return;
        }
    }

  

    private void askTOCreateContact(EJForm form)
    {
        EJQuestion question = new EJQuestion(form, QUESTION_ASK_CREATE_CUSTOMER_CONTACT, "Question", new EJMessage("Do you want to create a Cutomer Default Contact ?"), "Yes", "No");
        form.askQuestion(question);
    }
    
    private void askTOCreateCustomer(EJForm form)
    {
        EJQuestion question = new EJQuestion(form, QUESTION_ASK_CREATE_CUSTOMER, "Question", new EJMessage("No Cutomers Found, Do you want to create a Cutomer?"), "Yes", "No");
        form.askQuestion(question);
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals(QUESTION_ASK_CREATE_CUSTOMER_CONTACT))
        {
            if (question.getAnswer() == EJQuestionButton.ONE)
            {
                question.getForm().showPopupCanvas(F_CUSTOMER.C_CUSTOMER_CONTACTS_POPUP);
                question.getForm().getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).enterInsert(false);
            }
            return;
        }
        if (question.getName().equals(QUESTION_ASK_CREATE_CUSTOMER))
        {
            if (question.getAnswer() == EJQuestionButton.ONE)
            {
                question.getForm().getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterInsert(false);
            }
            return;
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
         form.saveChanges();
       

    }
    
   
    
    

    
        






}
