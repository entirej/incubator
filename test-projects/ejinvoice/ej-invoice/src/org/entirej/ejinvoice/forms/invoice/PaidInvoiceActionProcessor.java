package org.entirej.ejinvoice.forms.invoice;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_PAID_INVOICES;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJScreenType;

public class PaidInvoiceActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.setFormParameter(F_PAID_INVOICES.P_ITEMS_TO_INVOICE, 1);
    }

    @Override
    public void lovCompleted(EJForm form, EJScreenItem screenItem, boolean valueChosen) throws EJActionProcessorException
    {

    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_PAID_INVOICES.AC_SHOW_INVOICE.equals(command))
        {
//            InvoiceReport.downloadReport(new InvoiceService().getInvoicPDF(form, (int) record.getValue(F_INVOICE.B_INVOICE_HISTORY.I_ID)), record.getValue(F_INVOICE.B_INVOICE_HISTORY.I_NR) + ".pdf");
        }
        else if (F_PAID_INVOICES.AC_REFRESH_PAID_INVOICES.equals(command))
        {
            form.getBlock(F_PAID_INVOICES.B_INVOICE_HISTORY_PAID.ID).executeQuery();
        }
    }

//    @Override
//    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
//    {
//        if (question.getName().equals("ASK_DELETE_INVOICE") && question.getAnswer().equals(EJQuestionButton.ONE))
//        {
//            Integer id = (Integer)question.getForm().getBlock(F_INVOICE.B_INVOICE_HISTORY.ID).getFocusedRecord().getValue(F_INVOICE.B_INVOICE_HISTORY.I_ID);
//            new InvoiceService().deleteInvoice(question.getForm(), id);
//            question.getForm().getBlock(F_INVOICE.B_INVOICE_HISTORY.ID).executeQuery();
//        }
//    }

//
//    @Override
//    public void postFormSave(EJForm form) throws EJActionProcessorException
//    {
//        if (invoiceUpdated)
//        {
//            invoiceUpdated = false;
//            if (updatedInvoiceId != null)
//            {
//                new InvoiceService().updateInvoicPDF(form, updatedInvoiceId, InvoiceReport.generateInvoicePDF(form.getConnection(), updatedInvoiceId, updatedInvoiceLocale));
//                updatedInvoiceId = null;
//                updatedInvoiceLocale = null;
//            }
//            form.getBlock(F_INVOICE.B_INVOICE_HISTORY.ID).executeQuery();
//        }
//    }
//    
 
}
