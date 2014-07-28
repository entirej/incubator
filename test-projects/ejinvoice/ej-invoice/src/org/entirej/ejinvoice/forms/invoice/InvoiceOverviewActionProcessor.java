package org.entirej.ejinvoice.forms.invoice;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_OVERVIEW;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;

public class InvoiceOverviewActionProcessor extends EJDefaultFormActionProcessor
{

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_INVOICE_OVERVIEW.B_PROJECT_TIME.ID).executeQuery();
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        Object value = record.getValue(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_HEADER_ROW);
        if (value!=null && (Integer) value == 1)
        {
            record.getItem(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
            record.getItem(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_WORK_HOURS).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
            record.getItem(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_VAT_RATE).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
            record.getItem(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_TASK_PAY_RATE).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
            record.getItem(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_TOTAL_EXCL_VAT).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
            record.getItem(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_TOTAL_INCL_VAT).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
        }
    }
}
