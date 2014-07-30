package org.entirej.ejinvoice.forms.invoice;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_OVERVIEW;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.enumerations.EJScreenType;

public class InvoiceOverviewActionProcessor extends EJDefaultFormActionProcessor
{

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_INVOICE_OVERVIEW.AC_REQUERY_CUSTOMERS.equals(command))
        {
            form.getBlock(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.ID).executeQuery();
        }
        else if (F_INVOICE_OVERVIEW.AC_REQUERY_OPEN_POSITIONS.equals(command))
        {
            form.getBlock(F_INVOICE_OVERVIEW.B_PROJECT_TIME.ID).executeQuery();
            
            String name = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_NAME);
            String address1 = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_ADDRESS_LINE_1);
            String address2 = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_ADDRESS_LINE_2);
            String address3 = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_ADDRESS_LINE_3);
            String zip = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_POST_CODE);
            String town = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_TOWN);
            String country = (String) record.getValue(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_COUNTRY);

            StringBuilder str = new StringBuilder();
            str.append(name);
            str.append("\n");
            if (address1 != null)
            {
                str.append(address1);
                str.append("\n");
            }
            if (address2 != null)
            {
                str.append(address2);
                str.append("\n");
            }
            if (address3 != null)
            {
                str.append(address3);
                str.append("\n");
            }
            str.append(zip);
            str.append(" ");
            str.append(town);
            str.append(" ");
            str.append(country);

            form.getBlock(F_INVOICE_OVERVIEW.B_CHOSEN_CUSTOMER.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_OVERVIEW.B_CHOSEN_CUSTOMER.I_CUSTOMER_INFORMATION).setValue(str.toString());
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_INVOICE_OVERVIEW.B_PROJECT_TIME.ID.equals(record.getBlockName()))
        {
            Object value = record.getValue(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_HEADER_ROW);
            if (value != null && (Integer) value == 1)
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
}
