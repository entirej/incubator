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

import java.math.BigDecimal;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_INVOICE;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class InvoiceFormActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{

    public static final String QUESTION_ASK_CREATE_INVOICE  = "CREATE_INVOICE";
    public static final String QUESTION_ASK_CREATE_POSITION = "CREATE_INVOICE_POSITION";

    @Override
    public void preQuery(EJForm form, EJQueryCriteria queryCriteria) throws EJActionProcessorException
    {
        if (F_INVOICE.B_INVOICE.ID.equals(queryCriteria.getBlockName()))
        {
            EJBlock filterBlock = form.getBlock(F_INVOICE.B_INVOICE_FILTER.ID);
            EJScreenItem customerFIlter = filterBlock.getScreenItem(EJScreenType.MAIN, F_INVOICE.B_INVOICE_FILTER.I__CUSTOMER);
            if (customerFIlter.getValue() != null)
            {
                queryCriteria.add(EJRestrictions.equals(F_INVOICE.B_INVOICE.I_CUST_ID, customerFIlter.getValue()));
            }
        }
    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_INVOICE.B_INVOICE_FILTER.I__CUSTOMER.equals(itemName))
        {
            form.getBlock(F_INVOICE.B_INVOICE.ID).executeQuery();
            if (form.getBlock(F_INVOICE.B_INVOICE.ID).getBlockRecords().isEmpty())
            {
                askTOCreateCustomerInvoice(form);
            }
        }

        // if hours worked value in the following screen items change then
        // trigger the amount calculation
        if (F_INVOICE.B_INVOICE_POSITIONS.I_HOURS_WORKED.equals(itemName) || F_INVOICE.B_INVOICE_POSITIONS.I_CUPR_ID.equals(itemName))
        {
            // set the calculated amount to the record instead of screen
            // item due to amount display field being a non block service
            // item
            record.setValue(F_INVOICE.B_INVOICE_POSITIONS.I_AMOUNT, calculateInvPosAmount(record));
        }
    }

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
            askTOCreateCustomerInvoice(form);
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

        String blockName = null;
        if (record != null)
        {
            blockName = record.getBlockName();
        }
        if (blockName == null && form.getFocusedBlock() != null)
        {
            blockName = form.getFocusedBlock().getName();
        }

        if (blockName == null)
        {
            return;
        }

        if (F_INVOICE.AC_SHOW_POSITIONS.equals(command))
        {
            form.showPopupCanvas(F_INVOICE.C_POSITIONS_POPUP);
        }
        if (F_INVOICE.AC_INV_REFRESH.equals(command))
        {
            if (F_INVOICE.B_INVOICE.ID.equals(blockName) || F_INVOICE.B_INVOICE_FILTER.ID.equals(blockName))
            {
                form.getBlock(F_INVOICE.B_INVOICE.ID).executeLastQuery();
            }
        }
        if (F_INVOICE.AC_NEW.equals(command))
        {
            if (F_INVOICE.B_INVOICE.ID.equals(blockName) || F_INVOICE.B_INVOICE_FILTER.ID.equals(blockName))
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
            if (F_INVOICE.B_INVOICE.ID.equals(blockName) || F_INVOICE.B_INVOICE_FILTER.ID.equals(blockName))
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
            if (F_INVOICE.B_INVOICE.ID.equals(blockName) || F_INVOICE.B_INVOICE_FILTER.ID.equals(blockName))
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

    private void askTOCreateCustomerInvoice(EJForm form)
    {

        String message = "No Invoices Found, Do you want to create an Invoice?";
        EJBlock filterBlock = form.getBlock(F_INVOICE.B_INVOICE_FILTER.ID);
        EJScreenItem customerFIlter = filterBlock.getScreenItem(EJScreenType.MAIN, F_INVOICE.B_INVOICE_FILTER.I__CUSTOMER);
        if (customerFIlter.getValue() != null)
        {
            message = "No Invoices Found for Selected Customer, Do you want to create an Invoice ?";
        }

        EJQuestion question = new EJQuestion(form, QUESTION_ASK_CREATE_INVOICE, "Question", new EJMessage(message), "Yes", "No");
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
    public void initialiseRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        if (recordType == EJRecordType.INSERT && F_INVOICE.B_INVOICE.ID.equals(record.getBlockName()))
        {
            EJBlock filterBlock = form.getBlock(F_INVOICE.B_INVOICE_FILTER.ID);
            EJScreenItem customerFIlter = filterBlock.getScreenItem(EJScreenType.MAIN, F_INVOICE.B_INVOICE_FILTER.I__CUSTOMER);
            if (customerFIlter.getValue() != null)
            {
                record.setValue(F_INVOICE.B_INVOICE.I_CUST_ID, customerFIlter.getValue());
            }
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        form.saveChanges();
        refreshInvPosTotals(form, record);
        updateInvoiceAmounts(form, record);
    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // all open changes within the form should be saved
        form.saveChanges();
        // refresh the invoice position totals after a new invoice position has
        // been created
        refreshInvPosTotals(form, record);
        // update the invoice amounts after a new invoice position has been
        // created
        updateInvoiceAmounts(form, record);
    }

    @Override
    public void postUpdate(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // all open changes within the form should be saved
        form.saveChanges();
        // refresh the invoice position totals after an invoice position has
        // been updated
        refreshInvPosTotals(form, record);
        // update the invoice amounts after an invoice position has been updated
        updateInvoiceAmounts(form, record);
    }

    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // refresh the invoice position totals after any change to record
        refreshInvPosTotals(form, record);

    }

    /**
     * This method is called after any database call to insert/update/delete of
     * invoice position record to calculate the invoice position amount
     * 
     * @param form
     *            - invoice position form
     * @param record
     *            - invoice position record
     */
    private void refreshInvPosTotals(EJForm form, EJRecord record)
    {
        if (record.getBlockName() != null && record.getBlockName().equals(F_INVOICE.B_INVOICE_POSITIONS.ID))
        {

            for (EJRecord invPosRec : form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).getBlockRecords())
            {
                invPosRec.setValue(F_INVOICE.B_INVOICE_POSITIONS.I_AMOUNT, calculateInvPosAmount(invPosRec));

            }
        }
    }

    /**
     * This method is used to calculate the amount for a specific invoice
     * position
     * 
     * @param invPosRec
     *            - the invoice position record
     * @return
     */
    private Double calculateInvPosAmount(EJRecord invPosRec)
    {
        Double amount = 0.00;
        // if the invoice position record is null return 0 as the amount
        if (invPosRec == null)
        {
            return amount;
        }

        java.math.BigDecimal hrsWorked = (java.math.BigDecimal) invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_HOURS_WORKED);

        if (hrsWorked != null && hrsWorked.doubleValue() > 0)
        {

            final java.math.BigDecimal payRate = (java.math.BigDecimal) invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_CUST_PROJ_PAY_RATE);

            if (payRate == null)
            {
                return amount;
            }
            amount = payRate.doubleValue() * hrsWorked.doubleValue();

        }
        return amount;

    }

    /**
     * This method is called after any database call to insert/update/delete of
     * invoice record to update the invoice amounts and store in the database.
     * 
     * @param form
     *            - invoice form
     * @param record
     *            - invoice position record
     * @throws EJActionProcessorException
     */
    private void updateInvoiceAmounts(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (record.getBlockName() != null && record.getBlockName().equals(F_INVOICE.B_INVOICE_POSITIONS.ID))
        {

            // get the selected invoice record
            EJRecord invRec = form.getBlock(F_INVOICE.B_INVOICE.ID).getFocusedRecord();
            if (invRec == null)
            {
                return;
            }

            for (EJRecord invPosRec : form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).getBlockRecords())
            {
                invPosRec.setValue(F_INVOICE.B_INVOICE_POSITIONS.I_AMOUNT, calculateInvPosAmount(invPosRec));

            }
            Double amount = 0.00;
            Double amtWithVAT = 0.00;
            final EJManagedFrameworkConnection connection = form.getConnection();
            try
            {

                // calculate the amount and amount including vat
                for (EJRecord invPosRec : form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).getBlockRecords())
                {
                    Double recAmount = (Double) invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_AMOUNT);
                    if (recAmount == null)
                        continue;
                    amount += recAmount;
                    double vatAmount = 0;
                    if (recAmount != null && invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_VAT_RATE) != null)
                        vatAmount = ((Double) invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_AMOUNT) * ((java.math.BigDecimal) invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_VAT_RATE)).doubleValue()) / 100;
                    amtWithVAT += (Double) invPosRec.getValue(F_INVOICE.B_INVOICE_POSITIONS.I_AMOUNT) + vatAmount;
                }

                // query the database and get the matching invoice record and
                // update it's amount values
                EJStatementExecutor statementExecutor = new EJStatementExecutor();
                EJQueryCriteria criteria = new EJQueryCriteria();
                criteria.add(EJRestrictions.equals("ID", invRec.getValue(F_INVOICE.B_INVOICE.I_ID)));
                statementExecutor.executeUpdate(form, "INVOICE", criteria, new EJStatementParameter[] { new EJStatementParameter("AMOUNT_EXCL_VAT", Double.class, amount), new EJStatementParameter("AMOUNT_INCL_VAT", Double.class, amtWithVAT) });

                // commit the database transaction
                connection.commit();

                invRec.setValue(F_INVOICE.B_INVOICE.I_AMOUNT_INCL_VAT, new BigDecimal(amtWithVAT));
                invRec.setValue(F_INVOICE.B_INVOICE.I_AMOUNT_EXCL_VAT, new BigDecimal(amount));
            }
            catch (Exception e)
            {
                connection.rollback();
                throw new EJActionProcessorException(e.getMessage());
            }
            finally
            {
                connection.close();
            }

        }

    }

}
