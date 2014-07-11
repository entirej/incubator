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
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class InvoiceFormActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);

        EJBlock customerBlock = form.getBlock(F_INVOICE.B_INVOICE.ID);
        customerBlock.executeQuery();

    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_INVOICE.AC_TOOLBAR_HOME.equals(command))
        {
            form.openForm(F_TIME_ENTRY.ID);
            return;
        }
        if (F_INVOICE.AC_TOOLBAR_NEW.equals(command))
        {
            form.getBlock(F_INVOICE.B_INVOICE.ID).enterInsert(false);
            return;
        }
        if (F_INVOICE.AC_TOOLBAR_EDIT.equals(command))
        {
            form.getBlock(F_INVOICE.B_INVOICE.ID).enterUpdate();
            return;
        }
        if (F_INVOICE.AC_TOOLBAR_DELETE.equals(command) && form.getBlock(F_INVOICE.B_INVOICE.ID).getFocusedRecord() != null)
        {
            // before deleting the selected record from database validate
            // and check if the record to be deleted has any FK constraints
            // usage with other table data and if so throw an exception and
            // block physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_INVOICE.B_INVOICE.ID).getFocusedRecord(), "INVOICE");

            // If you are using codes for you texts, pass the code to entirej so
            // that it can be
            // translate by your application tanslator:
            //
            // String translatedText =
            // form.translateMessageText("askToDeleteCustomer");
            //
            // form more information on EntireJ's translators read:
            // http://http://docs.entirej.com/display/EJ1/EntireJ+Translator

            form.getBlock(F_INVOICE.B_INVOICE.ID).askToDeleteCurrentRecord("Are you sure you want to delete this invoice?");
            return;
        }

        else if (record.getBlockName() != null && ((record.getBlockName().equals(F_INVOICE.B_INVOICE_POSITIONS.ID)) || record.getBlockName().equals(F_INVOICE.B_INVOICE_POSITION_TOOL_BAR.ID)))
        {
            if (F_INVOICE.AC_NEW_POSITION.equals(command))
            {
                form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).enterInsert(false);
                return;
            }
            if (F_INVOICE.AC_EDIT_POSITION.equals(command))
            {
                EJBlock contactBlock = form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID);
                contactBlock.enterUpdate();
                return;
            }
            if (F_INVOICE.AC_DELETE_POSITION.equals(command) && form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).getFocusedRecord() != null)
            {
                form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this position?");
                return;
            }
        }

    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // all open changes within the form should be saved
        form.saveChanges();
        if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(record.getBlockName()))
        {
            updateInvPositionsToolbar(form);
        }
        

        refreshInvPosTotals(form, record);
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

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();
        if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(record.getBlockName()))
        {
            updateInvPositionsToolbar(form);
        }


        refreshInvPosTotals(form, record);
        updateInvoiceAmounts(form, record);
        
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(record.getBlockName()))
        {
            updateInvPositionsToolbar(form);
        }

    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        if (F_INVOICE.B_INVOICE.ID.equals(block.getName()))
        {
            updateInvPositionsToolbar(form);
        }
        else if (F_INVOICE.B_INVOICE_POSITIONS.ID.equals(block.getName()))
        {
            updateInvPositionsToolbar(form);
        }
    }


    private void updateInvPositionsToolbar(EJForm form)
    {

        if (form.getBlock(F_INVOICE.B_INVOICE_POSITION_TOOL_BAR.ID) != null)
        {
            EJBlock customerContactBlock = form.getBlock(F_INVOICE.B_INVOICE_POSITIONS.ID);
            final EJRecord contactRecord = customerContactBlock.getFocusedRecord();

           

            EJBlock toolbarBlock = form.getBlock(F_INVOICE.B_INVOICE_POSITION_TOOL_BAR.ID);
            toolbarBlock.getScreenItem(EJScreenType.MAIN, F_INVOICE.B_INVOICE_POSITION_TOOL_BAR.I_NEW).setEditable(form.getBlock(F_INVOICE.B_INVOICE.ID).getFocusedRecord()!=null);
            toolbarBlock.getScreenItem(EJScreenType.MAIN, F_INVOICE.B_INVOICE_POSITION_TOOL_BAR.I_DELETE).setEditable(contactRecord != null);
            toolbarBlock.getScreenItem(EJScreenType.MAIN, F_INVOICE.B_INVOICE_POSITION_TOOL_BAR.I_EDIT).setEditable(contactRecord != null);
        }

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
