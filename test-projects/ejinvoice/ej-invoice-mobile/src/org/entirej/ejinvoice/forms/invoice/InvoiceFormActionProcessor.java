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
package org.entirej.ejinvoice.forms.invoice;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_INVOICE;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;

public class InvoiceFormActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{

    public static final String QUESTION_ASK_CREATE_INVOICE  = "CREATE_INVOICE";
    public static final String QUESTION_ASK_CREATE_POSITION = "CREATE_INVOICE_POSITION";

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);

        EJBlock customerBlock = form.getBlock(F_INVOICE.B_INVOICE.ID);
        customerBlock.executeQuery();

    }

    @Override
    public void focusGained(EJForm form) throws EJActionProcessorException
    {
        if (form.getBlock(F_INVOICE.B_INVOICE.ID).getBlockRecords().isEmpty())
        {
            askTOCreateCustomer(form);
        }

    }

    @Override
    public void popupCanvasOpened(EJForm form, String popupCanvasName) throws EJActionProcessorException
    {
        if (form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).getBlockRecords().isEmpty())
        {
            askTOCreatePosition(form);
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

        // if (F_INVOICE.AC_SHOW_CONTACTS.equals(command))
        // {
        // form.showPopupCanvas(F_INVOICE.C_CUSTOMER_CONTACTS_POPUP);
        // }
        if (F_INVOICE.AC_NEW.equals(command))
        {
            if (F_INVOICE.B_INVOICE.ID.equals(blockName))
            {
                form.getBlock(F_INVOICE.B_INVOICE.ID).enterInsert(false);
            }
            else if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(blockName))
            {
                form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).enterInsert(false);
            }
            return;
        }
        if (F_INVOICE.AC_EDIT.equals(command))
        {
            if (F_INVOICE.B_INVOICE.ID.equals(blockName))
            {
                form.getBlock(F_INVOICE.B_INVOICE.ID).enterUpdate();
            }
            else if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(blockName))
            {
                form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).enterUpdate();
            }
            return;
        }
        if (F_INVOICE.AC_DELETE.equals(command) && form.getBlock(F_INVOICE.B_INVOICE.ID).getFocusedRecord() != null)
        {
            if (F_INVOICE.B_INVOICE.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_INVOICE.B_INVOICE.ID).getFocusedRecord(), "CUSTOMER");
                form.getBlock(F_INVOICE.B_INVOICE.ID).askToDeleteCurrentRecord("Are you sure you want to delete this Invoice?");
            }
            else if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).getFocusedRecord(), "CUSTOMER_CONTACT");
                form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this postion?");
            }

            return;
        }
    }

    private void askTOCreatePosition(EJForm form)
    {
        EJQuestion question = new EJQuestion(form, QUESTION_ASK_CREATE_POSITION, "Question", new EJMessage("Do you want to create an Invoce Position?"), "Yes", "No");
        form.askQuestion(question);
    }

    private void askTOCreateCustomer(EJForm form)
    {
        EJQuestion question = new EJQuestion(form, QUESTION_ASK_CREATE_INVOICE, "Question", new EJMessage("No Invoices Found, Do you want to create an Invoice?"), "Yes", "No");
        form.askQuestion(question);
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals(QUESTION_ASK_CREATE_POSITION))
        {
            if (question.getAnswer() == EJQuestionButton.ONE)
            {
                question.getForm().getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).enterInsert(false);
            }
            return;
        }
        if (question.getName().equals(QUESTION_ASK_CREATE_INVOICE))
        {
            if (question.getAnswer() == EJQuestionButton.ONE)
            {
                question.getForm().getBlock(F_INVOICE.B_INVOICE.ID).enterInsert(false);
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