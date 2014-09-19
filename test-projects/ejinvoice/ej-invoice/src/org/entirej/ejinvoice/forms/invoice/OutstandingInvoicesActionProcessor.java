package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Locale.Builder;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_OUTSTANDING_INVOICES;
import org.entirej.ejinvoice.forms.projects.reports.InvoiceReport;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJPopupButton;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;

public class OutstandingInvoicesActionProcessor extends DefaultFormActionProcessor
{
    private BigDecimal markedForInvoiceAmount = new BigDecimal(0);
    private boolean invoiceUpdated       = false;
    private Integer updatedInvoiceId     = null;
    private Locale  updatedInvoiceLocale = null;


    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.setFormParameter(F_OUTSTANDING_INVOICES.P_ITEMS_TO_INVOICE, 1);
    }

    @Override
    public void lovCompleted(EJForm form, EJScreenItem screenItem, boolean valueChosen) throws EJActionProcessorException
    {

    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID.equals(record.getBlockName()))// || F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY_PAID.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).equals("DRAFT"))
            {
                record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOIEC_STATUS_DRAFT);
                record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_DELETE).setValue("/icons/delete10.png");
            }
            else if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).equals("SENT"))
            {
                record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOICE_STATUS_SENT);
            }
            else if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).equals("LATE"))
            {
                record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOICE_STATUS_LATE);
            }
            else if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).equals("PAID"))
            {
                record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOICE_STATUS_PAID);
            }
            
            if (F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID.equals(record.getBlockName()))
            {
                if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).equals("DRAFT"))
                {
                    record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ACTION).setValue("Mark as sent");
                }
                else if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_STATUS).equals("SENT"))
                {
                    record.getItem(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ACTION).setValue("Record Payment");
                }
            }
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_OUTSTANDING_INVOICES.AC_SHOW_INVOICE.equals(command))
        {
            InvoiceReport.downloadReport(new InvoiceService().getInvoicPDF(form, (int) record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ID)), record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_NR) + ".pdf");
        }
        else if (F_OUTSTANDING_INVOICES.AC_INVOICE_HISTORY_STATUS_CHANGED.equals(command))
        {
            form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).executeQuery();
        }
        else if (F_OUTSTANDING_INVOICES.AC_UPDATE_INVOICE.equals(command))
        {
            form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).enterUpdate();
        }
        else if (F_OUTSTANDING_INVOICES.AC_DELETE_INVOICE.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_INVOICE");
            question.setMessage(new EJMessage("Are you sure you want to delete this invoice?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_OUTSTANDING_INVOICES.AC_INVOICE_ACTION.equals(command))
        {
            if (((String)record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ACTION)).equalsIgnoreCase("Mark as Sent"))
            {
                form.getBlock(F_OUTSTANDING_INVOICES.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_SEND_INVOICE.I_SEND_DATE).setValue(new Date(System.currentTimeMillis()));
                form.getBlock(F_OUTSTANDING_INVOICES.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_SEND_INVOICE.I_NOTES).setValue(record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_NOTES));
                form.showPopupCanvas(F_OUTSTANDING_INVOICES.C_SEND_INVOICE_POPUP);
            }
            else if (((String)record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ACTION)).equalsIgnoreCase("Record Payment"))
            {
                form.getBlock(F_OUTSTANDING_INVOICES.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_PAY_INVOICE.I_PAYMENT_DATE).setValue(new Date(System.currentTimeMillis()));
                form.getBlock(F_OUTSTANDING_INVOICES.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_PAY_INVOICE.I_NOTES).setValue(record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_NOTES));
                form.showPopupCanvas(F_OUTSTANDING_INVOICES.C_PAY_INVOICE_POPUP);
            }

        }
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_INVOICE") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            Integer id = (Integer)question.getForm().getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).getFocusedRecord().getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ID);
            new InvoiceService().deleteInvoice(question.getForm(), id);
            question.getForm().getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).executeQuery();
        }
    }


    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID.equals(block.getName()))
        {
            if (EJScreenType.UPDATE.equals(screenType))
            {
                Integer sent = (Integer) record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_SENT);
                if (sent == 0)
                {
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_INV_DATE).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_DUE_DATE).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_NR).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_INVOICE_ADDRESS).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_SUMMARY).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_FOOTER).setEditable(true);
                }
                else
                {
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_INV_DATE).setDisplayProperty("DISPLAY_VALUE_AS_LABEL", "true");
                    
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_INV_DATE).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_DUE_DATE).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_NR).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_INVOICE_ADDRESS).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_SUMMARY).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_FOOTER).setEditable(false);
                }
            }
        }
    }
    
    @Override
    public void postUpdate(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID.equals(record.getBlockName()))
        {
            invoiceUpdated = true;

            if (record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_SENT).equals(0))
            {
                updatedInvoiceId = (Integer) record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_ID);
                String localeLanguage = (String) record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_LOCALE_LANGUAGE);
                String localeCountry = (String) record.getValue(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.I_LOCALE_COUNTRY);
                updatedInvoiceLocale = new Builder().setLanguage(localeLanguage).setRegion(localeCountry).build();
            }
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (invoiceUpdated)
        {
            invoiceUpdated = false;
            if (updatedInvoiceId != null)
            {
                new InvoiceService().updateInvoicPDF(form, updatedInvoiceId, InvoiceReport.generateInvoicePDF(form.getConnection(), updatedInvoiceId, updatedInvoiceLocale));
                updatedInvoiceId = null;
                updatedInvoiceLocale = null;
            }
            form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).executeQuery();
        }
    }
    
    @Override
    public void popupCanvasClosing(EJForm form, String popupCanvasName, EJPopupButton button) throws EJActionProcessorException
    {
    
        if (F_OUTSTANDING_INVOICES.C_SEND_INVOICE_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Date sendDate = (Date)form.getBlock(F_OUTSTANDING_INVOICES.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_SEND_INVOICE.I_SEND_DATE).getValue();
            String notes = (String)form.getBlock(F_OUTSTANDING_INVOICES.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_SEND_INVOICE.I_NOTES).getValue();
            
            if (sendDate == null)
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "Please enter the date when the invoice was sent"));
            }
            
            InvoiceHistory invPos = (InvoiceHistory)form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).getFocusedRecord().getBlockServicePojo();
            invPos.setSentDate(sendDate);
            invPos.setNotes(notes);
            invPos.setSent(1);
            
            ArrayList<InvoiceHistory> pojos = new ArrayList<InvoiceHistory>();
            pojos.add(invPos);
            
            new InvoiceHistoryBlockService().executeUpdate(form, pojos);
            
            form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).executeQuery();
        }
        else if (F_OUTSTANDING_INVOICES.C_PAY_INVOICE_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Date payDate = (Date)form.getBlock(F_OUTSTANDING_INVOICES.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_PAY_INVOICE.I_PAYMENT_DATE).getValue();
            String notes = (String)form.getBlock(F_OUTSTANDING_INVOICES.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_OUTSTANDING_INVOICES.B_PAY_INVOICE.I_NOTES).getValue();
            
            if (payDate == null)
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "Please enter the date when the invoice was paid"));
            }
            
            InvoiceHistory invPos = (InvoiceHistory)form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).getFocusedRecord().getBlockServicePojo();
            invPos.setPaymentDate(payDate);
            invPos.setNotes(notes);
            invPos.setPaid(1);
            
            ArrayList<InvoiceHistory> pojos = new ArrayList<InvoiceHistory>();
            pojos.add(invPos);
            
            new InvoiceHistoryBlockService().executeUpdate(form, pojos);
            
            form.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).executeQuery();
        }
        
    }

}
